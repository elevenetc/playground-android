package su.levenetc.androidplayground.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.maps.MapView;

/**
 * Created by eugene.levenetc on 17/01/2017.
 */
public class ExtendedMapView extends MapView {

	//private GeoPoint lastMapCenter;
	private boolean isTouchEnded;
	private boolean isFirstComputeScroll;

	public ExtendedMapView(Context context) {
		super(context);
	}

	public ExtendedMapView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN)
			this.isTouchEnded = false;
		else if (event.getAction() == MotionEvent.ACTION_UP)
			this.isTouchEnded = true;
		else if (event.getAction() == MotionEvent.ACTION_MOVE)
			this.isFirstComputeScroll = true;





		return super.onTouchEvent(event);
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
//		if (this.isTouchEnded && this.lastMapCenter.equals(this.getMapCenter()) && this.isFirstComputeScroll) {
//			// here you use this.getMapCenter() (e.g. call onEndDrag method)
//			this.isFirstComputeScroll = false;
//		} else{
//			this.lastMapCenter = this.getMapCenter();
//		}

	}

	@Override public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
		Log.d("onNestedFling", "velocityX:" + velocityX + " velocityY:" + velocityY);
		return super.onNestedFling(target, velocityX, velocityY, consumed);
	}

	@Override public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
		Log.d("onNestedPreFling", "velocityX:" + velocityX + " velocityY:" + velocityY);
		return super.onNestedPreFling(target, velocityX, velocityY);
	}

	@Override public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
		Log.d("dispatchNestedFling", "velocityX:" + velocityX + " velocityY:" + velocityY);
		return super.dispatchNestedFling(velocityX, velocityY, consumed);
	}

	@Override public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
		Log.d("dispatchNestedPreFling", "velocityX:" + velocityX + " velocityY:" + velocityY);
		return super.dispatchNestedPreFling(velocityX, velocityY);
	}
}
