package su.levenetc.androidplayground.touchdiffuser;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import su.levenetc.androidplayground.utils.ViewUtils;

public class TouchDiffuser {

	public static TouchDiffuser inst = new TouchDiffuser();
	private String TAG = "touch";

	public boolean onInterceptTouchEvent(ViewGroup viewGroup, MotionEvent ev) {
		Log.d("touch", "group:onInterceptTouchEvent:" + MotionEvent.actionToString(ev.getAction()));

		return true;
	}

	public boolean onTouchEvent(ViewGroup view, MotionEvent event) {
		boolean result = internalTouchEventHandler(view, event, 0, 0);
		Log.d(TAG, "totalResult" + result);
		return result;
	}

	private boolean internalTouchEventHandler(ViewGroup view, MotionEvent event, float offX, float offY) {

		int source = event.getSource();

		if (source == 666) {
			return false;
		}

		Log.d("touch", "group:onTouchEvent:" + MotionEvent.actionToString(event.getAction()));

		int eventX = (int) event.getX();
		int eventY = (int) event.getY();

		for (int i = view.getChildCount() - 1; i >= 0; i--) {
			View child = view.getChildAt(i);

			float childX = child.getX() + offX;
			float childY = child.getY() + offY;
			int childWidth = child.getWidth();
			int childHeight = child.getHeight();
			boolean containsTouch = ViewUtils.isContains(eventX, eventY, childX, childY, childWidth, childHeight);

			if (containsTouch) {

				if (isTouchableViewGroup(child)) {
					dispatchEvent(event, child);
					return true;
				} else if (child instanceof ViewGroup) {
					boolean result = internalTouchEventHandler((ViewGroup) child, event, childX, childY);
					if (result) return true;
				} else {
					dispatchEvent(event, child);
					return true;
				}

			}

		}

		return false;
	}

	private void dispatchEvent(MotionEvent event, View view) {
		Log.d(TAG, "dispatch:" + ViewUtils.getId(view));
		NetworkEventsDiffuser.diffuse(event, view);
		view.dispatchTouchEvent(MotionEvent.obtain(event));
	}

	private boolean isTouchableViewGroup(View view) {
		return view instanceof ViewPager || view instanceof RecyclerView;
	}
}
