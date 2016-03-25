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

	private RecyclerViewScaleAdapter adapter;
	private ScaleGestureDetector scaleGestureDetector;
	private float scaleY = 1f;
	private List<RecyclerViewScaleAdapter.ScaleModel> models;
	private float MIN_SCALE = 0.5f;
	private float MAX_SCALE = 2.0f;

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


		models = new LinkedList<>();
		for (int i = 0; i < 50; i++)
			models.add(new RecyclerViewScaleAdapter.ScaleModel());

		adapter = new RecyclerViewScaleAdapter(this, models);
		setAdapter(adapter);

		scaleGestureDetector = RecyclerViewScaleDetector.create(getContext(), this, this, MIN_SCALE, MAX_SCALE);
	}

	private void applyYScale(float scaleY) {
		this.scaleY = scaleY;
		for (RecyclerViewScaleAdapter.ScaleModel model : models) model.scale = scaleY;
		adapter.notifyDataSetChanged();
	}

	@Override public void onRecyclerViewScaleY(float value) {
		applyYScale(value);
	}

	@Override public void onRecyclerViewScaleX(float value) {

	}

	public float getRecycleScaleY() {
		return scaleY;
	}

}