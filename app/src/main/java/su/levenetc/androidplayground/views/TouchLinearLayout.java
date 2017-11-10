package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import su.levenetc.androidplayground.touchdiffuser.TouchDiffuser;
import su.levenetc.androidplayground.utils.ViewUtils;

public class TouchLinearLayout extends LinearLayout {

	public TouchLinearLayout(Context context) {
		super(context);
		init();
	}

	public TouchLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setWillNotDraw(false);
	}

//	@Override public boolean onInterceptTouchEvent(MotionEvent ev) {
//		return TouchDiffuser.inst.onInterceptTouchEvent(this, ev);
//	}
//
//	@Override public boolean onTouchEvent(MotionEvent event) {
//		return TouchDiffuser.inst.onTouchEvent(this, event);
//	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		ViewUtils.drawDebugTree(this, canvas, 0, 0);
	}
}
