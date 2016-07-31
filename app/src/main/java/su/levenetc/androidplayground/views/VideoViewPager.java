package su.levenetc.androidplayground.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import su.levenetc.androidplayground.utils.ViewUtils;

/**
 * Created by Eugene Levenetc on 31/07/2016.
 */
public class VideoViewPager extends ViewPager {
	public VideoViewPager(Context context) {
		super(context);
	}

	public VideoViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		float screenWidth = ViewUtils.getScreenWidth(getContext());
		float height = screenWidth / (16f / 9f);

		super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY));
	}


}
