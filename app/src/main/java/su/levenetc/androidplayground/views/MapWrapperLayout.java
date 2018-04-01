package su.levenetc.androidplayground.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;

/**
 * Created by eugene.levenetc on 16/01/2017.
 */
public class MapWrapperLayout extends FrameLayout {

	private GestureDetector gestureDetector;

	public interface OnDragListener {
		void onDrag(MotionEvent motionEvent);
	}

	private VelocityTracker velocityTracker;
	private OnDragListener dragListener;

	public MapWrapperLayout(Context context) {
		super(context);
		init();
	}

	public MapWrapperLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		gestureDetector = new GestureDetector(getContext(), new MyOnGestureListener());
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		dragListener.onDrag(ev);

//		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//			if (velocityTracker != null) velocityTracker.clear();
//			velocityTracker = VelocityTracker.obtain();
//		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//			velocityTracker.addMovement(ev);
//			velocityTracker.computeCurrentVelocity(1000);
//		} else if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
//			Log.d("XVelocity", velocityTracker.getXVelocity() + "");
//			Log.d("YVelocity", velocityTracker.getYVelocity() + "");
//			velocityTracker.recycle();
//		}
//
		if(ev.getAction() == MotionEvent.ACTION_UP){
			ev.setAction(MotionEvent.ACTION_CANCEL);
		}
//
//		gestureDetector.onTouchEvent(ev);



		return super.dispatchTouchEvent(ev);
	}

	public void setOnDragListener(OnDragListener mOnDragListener) {
		this.dragListener = mOnDragListener;
	}

	class MyOnGestureListener implements GestureDetector.OnGestureListener {

		@Override public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override public void onShowPress(MotionEvent e) {

		}

		@Override public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return false;
		}

		@Override public void onLongPress(MotionEvent e) {

		}

		@Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.d("onFling", "FLING!");
			return false;
		}
	}
}