package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import su.levenetc.androidplayground.touchdiffuser.TouchDiffuser;
import su.levenetc.androidplayground.utils.Paints;

public class TouchLinearLayout extends LinearLayout {

	float touchX = 0;
	float touchY = 0;
	int actionType;

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

	@Override public boolean onInterceptTouchEvent(MotionEvent ev) {
		return TouchDiffuser.inst.onInterceptTouchEvent(this, ev);
	}

	//
	@Override public boolean onTouchEvent(MotionEvent event) {

		int source = event.getSource();
		if (source == 777) {
			touchX = event.getX();
			touchY = event.getY();
			actionType = event.getAction();
			post(new Runnable() {
				@Override public void run() {
					invalidate();
				}
			});
		}

		return TouchDiffuser.inst.onTouchEvent(this, event);
	}

	@Override protected void onDraw(Canvas canvas) {

		if (actionType == MotionEvent.ACTION_DOWN) {
			canvas.drawCircle(touchX, touchY, 100, Paints.Fill.Red);
		} else if (actionType == MotionEvent.ACTION_MOVE) {
			canvas.drawCircle(touchX, touchY, 100, Paints.Fill.Blue);
		} else if (actionType == MotionEvent.ACTION_UP) {
			canvas.drawCircle(touchX, touchY, 100, Paints.Fill.Grey);
		} else {
			canvas.drawCircle(touchX, touchY, 100, Paints.Fill.Black);
		}

		super.onDraw(canvas);
		//ViewUtils.drawDebugTree(this, canvas, 0, 0);


	}
}
