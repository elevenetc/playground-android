package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;

import su.levenetc.androidplayground.models.TimeLine;
import su.levenetc.androidplayground.models.TimeLineSession;
import su.levenetc.androidplayground.models.TimeRange;

/**
 * Created by Eugene Levenetc on 28/03/2016.
 */
public class LineView extends View {

	private TimeLine timeItem;
	private Paint fillPaint = new Paint();

	public LineView(Context context) {
		super(context);
	}

	public LineView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void config() {
		fillPaint.setStyle(Paint.Style.FILL);
	}

	public void setTimeItem(TimeLine timeItem) {
		this.timeItem = timeItem;
		invalidate();
	}

	@Override protected void onDraw(Canvas canvas) {
		if (timeItem != null) {
			int width = canvas.getWidth();

			TimeLineSession session = timeItem.getSession();
			long sessionTime = session.getDuration();
			long startShift = session.getStartTimeOf(timeItem);
			LinkedList<TimeRange> timeRanges = timeItem.getTimeRanges();
			long startOffset = 0;
			for (TimeRange timeRange : timeRanges) {

				fillPaint.setColor(timeRange.getColor());
				float start = timeRange.getRelativeStartTime(startOffset + startShift, sessionTime);
				float end = timeRange.getRelativeEndTime(startOffset + startShift, sessionTime);
				canvas.drawRect(width * start, 0, width * end, canvas.getHeight(), fillPaint);

				startOffset += timeRange.getDuration();
			}
		}
	}
}
