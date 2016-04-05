package su.levenetc.androidplayground.prototypes.timelineview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import java.util.LinkedList;
import java.util.List;

import su.levenetc.androidplayground.adapters.RecyclerViewScaleAdapter;
import su.levenetc.androidplayground.utils.RecyclerViewScaleDetector;

/**
 * Created by Eugene Levenetc on 24/03/2016.
 */
public class ScalableRecyclerView extends RecyclerView implements RecyclerViewScaleDetector.ScaleHandler {

	private ScaleGestureDetector scaleGestureDetector;
	private static final float MIN_SCALE = 0.5f;
	private static final float MAX_SCALE = 2.0f;

	public ScalableRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ScalableRecyclerView(Context context) {
		super(context);
		init();
	}

	@Override public boolean onTouchEvent(MotionEvent e) {
		scaleGestureDetector.onTouchEvent(e);
		return e.getPointerCount() > 1 || super.onTouchEvent(e);
	}

	private void init() {
		setLayoutManager(new LinearLayoutManager(getContext()));
		scaleGestureDetector = RecyclerViewScaleDetector.create(getContext(), this, this, MIN_SCALE, MAX_SCALE);
	}

	private void applyYScale(float scaleY) {
		Adapter adapter = getAdapter();

		if (adapter != null) {
			((RecyclerViewScaleAdapter) adapter).updateScaleY(scaleY);
			adapter.notifyDataSetChanged();
		}
	}

	@Override public void onRecyclerViewScaleY(float value) {
		applyYScale(value);
	}

	@Override public void onRecyclerViewScaleX(float value) {

	}
}