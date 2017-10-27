package su.levenetc.androidplayground.views;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class TouchButton extends AppCompatButton {
	public TouchButton(Context context) {
		super(context);
	}

	public TouchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override public boolean onTouchEvent(MotionEvent event) {
		Log.d("touch", "button:onTouchEvent");
		return super.onTouchEvent(event);
	}
}
