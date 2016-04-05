package su.levenetc.androidplayground.utils;


import android.os.Handler;
import android.os.Looper;

import su.levenetc.androidplayground.adapters.RecyclerViewScaleAdapter;

/**
 * Created by Eugene Levenetc on 01/04/2016.
 */
public class SessionBus {

	Handler handler = new Handler(Looper.getMainLooper());
	private RecyclerViewScaleAdapter adapter;

	public SessionBus(RecyclerViewScaleAdapter adapter) {
		this.adapter = adapter;
	}

	public void notifyAddedTimeLine() {
		notifyDataSetChanged();
	}

	public void notifyPeriodStarted() {
		notifyDataSetChanged();
	}

	public void notifyPeriodEnded() {
		notifyDataSetChanged();
	}

	public void notifyAddedEvent() {
		notifyDataSetChanged();
	}

	private void notifyDataSetChanged() {
		handler.post(new Runnable() {
			@Override public void run() {
				adapter.notifyDataSetChanged();
			}
		});
	}
}
