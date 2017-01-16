package su.levenetc.androidplayground.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;

/**
 * Created by eugene.levenetc on 16/01/2017.
 */
public class MapWrapperLayout extends FrameLayout {

	public interface OnDragListener {
		public void onDrag(MotionEvent motionEvent);
	}

	private VelocityTracker velocityTracker;
	private OnDragListener dragListener;

	public MapWrapperLayout(Context context) {
		super(context);
	}

	public MapWrapperLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		dragListener.onDrag(ev);

		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (velocityTracker != null) velocityTracker.clear();
			velocityTracker = VelocityTracker.obtain();
		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			velocityTracker.addMovement(ev);
			velocityTracker.computeCurrentVelocity(1000);
		} else if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
			Log.d("XVelocity", velocityTracker.getXVelocity() + "");
			Log.d("YVelocity", velocityTracker.getYVelocity() + "");
			velocityTracker.recycle();
		}

		return super.dispatchTouchEvent(ev);
	}

	public void setOnDragListener(OnDragListener mOnDragListener) {
		this.dragListener = mOnDragListener;
	}
}