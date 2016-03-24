package com.squareup.picasso;

import android.content.Context;
import android.os.Handler;

import java.util.List;
import java.util.concurrent.ExecutorService;

import su.levenetc.androidplayground.utils.PlayUtils;

import static com.squareup.picasso.Utils.OWNER_DISPATCHER;
import static com.squareup.picasso.Utils.VERB_ENQUEUED;
import static com.squareup.picasso.Utils.VERB_IGNORED;
import static com.squareup.picasso.Utils.VERB_PAUSED;
import static com.squareup.picasso.Utils.log;

/**
 * Created by Eugene Levenetc on 24/03/2016.
 */
public class DispatcherWrapper extends Dispatcher {

	public static DispatcherWrapper cloneInstance(Dispatcher dispatcher) {
		return new DispatcherWrapper(
				dispatcher.context,
				dispatcher.service,
				dispatcher.mainThreadHandler,
				dispatcher.downloader,
				dispatcher.cache,
				dispatcher.stats
		);
	}

	private DispatcherWrapper(Context context, ExecutorService service, Handler mainThreadHandler, Downloader downloader, Cache cache, Stats stats) {
		super(context, service, mainThreadHandler, downloader, cache, stats);
	}

	@Override void performSubmit(Action action, boolean dismissFailed) {
		if (pausedTags.contains(action.getTag())) {
			pausedActions.put(action.getTarget(), action);
			if (action.getPicasso().loggingEnabled) {
				log(OWNER_DISPATCHER, VERB_PAUSED, action.request.logId(),
						"because tag '" + action.getTag() + "' is paused");
			}
			return;
		}

		BitmapHunter hunter = hunterMap.get(action.getKey());
		if (hunter != null) {
			hunter.attach(action);
			return;
		}

		if (service.isShutdown()) {
			if (action.getPicasso().loggingEnabled) {
				log(OWNER_DISPATCHER, VERB_IGNORED, action.request.logId(), "because shut down");
			}
			return;
		}

		hunter = createHunter(action.getPicasso(), this, cache, stats, action);
//		hunter = forRequest(action.getPicasso(), this, cache, stats, action);
		hunter.future = service.submit(hunter);
		hunterMap.put(action.getKey(), hunter);
		if (dismissFailed) {
			failedActions.remove(action.getTarget());
		}

		if (action.getPicasso().loggingEnabled) {
			log(OWNER_DISPATCHER, VERB_ENQUEUED, action.request.logId());
		}
	}

	private static BitmapHunter createHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
		Request request = action.getRequest();
		List<RequestHandler> requestHandlers = picasso.getRequestHandlers();

		// Index-based loop to avoid allocating an iterator.
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0, count = requestHandlers.size(); i < count; i++) {
			RequestHandler requestHandler = requestHandlers.get(i);
			if (requestHandler.canHandleRequest(request)) {
				return HunterWrapper.cloneInstance(new BitmapHunter(picasso, dispatcher, cache, stats, action, requestHandler));
			}
		}

		RequestHandler errorHandler = (RequestHandler) PlayUtils.getPrivateStaticFieldValueSilently(BitmapHunter.class, "ERRORING_HANDLER");
		return HunterWrapper.cloneInstance(new BitmapHunter(picasso, dispatcher, cache, stats, action, errorHandler));
	}
}