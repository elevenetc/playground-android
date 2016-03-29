package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import su.levenetc.androidplayground.models.TimeLine;

/**
 * Created by Eugene Levenetc on 24/03/2016.
 */
public class TimeLineItemView extends FrameLayout {

	private int id;
	private int defaultHeight = 200;
	private float scaleValueY = 1f;
	private TextView textView;
	private LineView lineView;

	public TimeLineItemView(Context context) {
		super(context);
		init();
	}

	private void init() {
		textView = new TextView(getContext());
		lineView = new LineView(getContext());
		textView.setTextColor(Color.WHITE);
		addView(lineView);
		addView(textView);

		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defaultHeight);
		setLayoutParams(lp);
	}

	public void setTimeLineItem(TimeLine item) {
		lineView.setTimeItem(item);
	}

	public void setItemId(int id) {
		this.id = id;
		textView.setText(String.valueOf(id));
		setBackgroundColor(id % 2 == 0 ? Color.BLACK : Color.DKGRAY);
	}

	@Override protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		updateScaledHeight();
	}

	public void setScaleValueY(float scaleValueY) {
		this.scaleValueY = scaleValueY;
		updateScaledHeight();
	}

	private void updateScaledHeight() {
		getLayoutParams().height = (int) (defaultHeight * scaleValueY);
		requestLayout();
	}

}
