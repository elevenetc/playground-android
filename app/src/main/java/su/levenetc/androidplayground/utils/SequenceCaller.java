package su.levenetc.androidplayground.utils;

import android.view.View;

import java.util.LinkedList;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class SequenceCaller {

	private View view;
	private LinkedList<CallObj> calls = new LinkedList<>();
	private int currentIndex = -1;

	public SequenceCaller(View view) {

		this.view = view;
	}

	public SequenceCaller add(Call call) {
		return add(call, 0);
	}

	public SequenceCaller add(Call call, long time) {
		calls.add(new CallObj(time, call));
		return this;
	}

	public void start() {
		next();
	}

	private void next() {
		currentIndex++;

		if (currentIndex > calls.size() - 1) return;

		CallObj callObj = calls.get(currentIndex);
		view.postDelayed(() -> {
			callObj.call.run();
			next();
		}, callObj.delay);
	}

	public interface Call {
		void run();
	}

	protected static class CallObj {
		long delay;
		Call call;

		public CallObj(long delay, Call call) {
			this.delay = delay;
			this.call = call;
		}
	}
}
