package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import su.levenetc.androidplayground.models.TimePeriod;
import su.levenetc.androidplayground.models.TimeSession;
import su.levenetc.androidplayground.models.Timeline;
import su.levenetc.androidplayground.utils.PlayUtils;

import static com.squareup.picasso.MemoryPolicy.shouldReadFromMemoryCache;
import static com.squareup.picasso.Picasso.LoadedFrom.MEMORY;
import static com.squareup.picasso.Utils.OWNER_HUNTER;
import static com.squareup.picasso.Utils.VERB_DECODED;
import static com.squareup.picasso.Utils.VERB_TRANSFORMED;
import static com.squareup.picasso.Utils.log;

/**
 * Created by Eugene Levenetc on 24/03/2016.
 */
public class HunterWrapper extends BitmapHunter {

	private static Object DECODE_LOCK;
	private TimeSession session;
	private Timeline timeline;

	public static HunterWrapper cloneInstance(BitmapHunter hunter, TimeSession session) {

		if (DECODE_LOCK == null)
			DECODE_LOCK = PlayUtils.getPrivateStaticFieldValueSilently(BitmapHunter.class, "DECODE_LOCK");

		return new HunterWrapper(
				hunter.picasso,
				hunter.dispatcher,
				hunter.cache,
				hunter.stats,
				hunter.action,
				hunter.requestHandler,
				session
		);
	}

	private HunterWrapper(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, RequestHandler requestHandler, TimeSession session) {
		super(picasso, dispatcher, cache, stats, action, requestHandler);
		this.session = session;
	}

	public Timeline getTimeline() {
		return timeline;
	}

	public TimeSession getSession() {
		return session;
	}

	@Override public void run() {
		super.run();
	}

	@Override Bitmap hunt() throws IOException {
		Bitmap bitmap = null;

		timeline = session.createTimeline(action.request.uri.toString());
		TimePeriod downloadPeriod = TimePeriod.create("download", Color.YELLOW);
		TimePeriod decodePeriod = TimePeriod.create("decode", Color.BLUE);
		TimePeriod transformPeriod = TimePeriod.create("trans", Color.GREEN);

		session.startPeriod(timeline, downloadPeriod);

		Log.i("PicassoInterceptor", "start:" + action.request.uri);

		if (shouldReadFromMemoryCache(memoryPolicy)) {
			bitmap = cache.get(key);
			if (bitmap != null) {
				stats.dispatchCacheHit();
				loadedFrom = MEMORY;
				if (picasso.loggingEnabled) {
					log(OWNER_HUNTER, VERB_DECODED, data.logId(), "from cache");
				}
				Log.i("PicassoInterceptor", "loadedFromCache:" + action.request.uri);
				Log.i("PicassoInterceptor", "end:" + action.request.uri);
				session.endPeriod(downloadPeriod);
				session.endTimeLine(timeline);
				return bitmap;
			}
		}

		data.networkPolicy = retryCount == 0 ? NetworkPolicy.OFFLINE.index : networkPolicy;
		RequestHandler.Result result = requestHandler.load(data, networkPolicy);
		if (result != null) {
			session.endPeriod(downloadPeriod);
			loadedFrom = result.getLoadedFrom();
			exifRotation = result.getExifOrientation();


			bitmap = result.getBitmap();

			// If there was no Bitmap then we need to decode it from the stream.
			if (bitmap == null) {
				InputStream is = result.getStream();
				try {
					Log.i("PicassoInterceptor", "decodingStart:" + action.request.uri);
					session.startPeriod(timeline, decodePeriod);
					bitmap = decodeStream(is, data);
				} finally {
					Utils.closeQuietly(is);
					Log.i("PicassoInterceptor", "decodingEnd:" + action.request.uri);
					session.endPeriod(decodePeriod);
				}
			}
		}

		if (bitmap != null) {
			if (picasso.loggingEnabled) {
				log(OWNER_HUNTER, VERB_DECODED, data.logId());
			}
			stats.dispatchBitmapDecoded(bitmap);
			if (data.needsTransformation() || exifRotation != 0) {
				Log.i("PicassoInterceptor", "transformationStart:" + action.request.uri);
				session.startPeriod(timeline, transformPeriod);
				synchronized (DECODE_LOCK) {
					if (data.needsMatrixTransform() || exifRotation != 0) {
						bitmap = transformResult(data, bitmap, exifRotation);
						if (picasso.loggingEnabled) {
							log(OWNER_HUNTER, VERB_TRANSFORMED, data.logId());
						}
					}
					if (data.hasCustomTransformations()) {
						bitmap = applyCustomTransformations(data.transformations, bitmap);
						if (picasso.loggingEnabled) {
							log(OWNER_HUNTER, VERB_TRANSFORMED, data.logId(), "from custom transformations");
						}
					}
				}
				Log.i("PicassoInterceptor", "transformationEnd:" + action.request.uri);
				session.endPeriod(transformPeriod);
				if (bitmap != null) {
					stats.dispatchBitmapTransformed(bitmap);
				}
			}
		}

		Log.i("PicassoInterceptor", "end:" + action.request.uri);
		session.endTimeLine(timeline);
		return bitmap;
	}
}