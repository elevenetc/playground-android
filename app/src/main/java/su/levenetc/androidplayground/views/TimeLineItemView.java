package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import su.levenetc.androidplayground.models.TimeSession;
import su.levenetc.androidplayground.models.Timeline;

/**
 * Created by Eugene Levenetc on 24/03/2016.
 */
public class TimeLineItemView extends FrameLayout {

	private int id;
	private int defaultHeight = 100;
	private float scaleValueY = 1f;
	private TextView textView;
	private TimelineView timelineView;

	public TimeLineItemView(Context context) {
		super(context);
		init();
	}

	private void init() {
		textView = new TextView(getContext());
		timelineView = new TimelineView(getContext());
		textView.setTextColor(Color.WHITE);
		addView(timelineView);
		addView(textView);

		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defaultHeight);
		setLayoutParams(lp);
	}

	public void setModels(TimeSession session, Timeline item) {
		timelineView.setModels(session, item);
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
