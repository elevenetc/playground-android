package su.levenetc.androidplayground.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import su.levenetc.androidplayground.R;

/**
 * Created by eugene.levenetc on 10/02/2017.
 */
public class StickyScrollView extends ScrollView {

	private View stickyView;
	private View viewGap;

	public StickyScrollView(Context context) {
		super(context);
	}

	public StickyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		final int scrollY = getScrollY();
		final int gapHeight = viewGap.getHeight();
		final int diff = gapHeight - scrollY;
		if (diff < 0) {
			stickyView.setTranslationY(Math.abs(diff));
		} else {
			stickyView.setTranslationY(0);
		}
	}

	@Override public boolean onTouchEvent(MotionEvent ev) {
		return super.onTouchEvent(ev);
	}

	@Override public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
	}

	@Override protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
	}

	@Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	@Override protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		stickyView = findViewById(R.id.stick);
		viewGap = findViewById(R.id.view_gap);
	}
}
