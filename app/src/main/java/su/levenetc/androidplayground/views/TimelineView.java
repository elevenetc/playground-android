package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;

import su.levenetc.androidplayground.models.TimePeriod;
import su.levenetc.androidplayground.models.TimeSession;
import su.levenetc.androidplayground.models.Timeline;
import su.levenetc.androidplayground.models.TimelineEvent;
import su.levenetc.androidplayground.utils.Out;

/**
 * Created by Eugene Levenetc on 28/03/2016.
 */
public class TimelineView extends View {

	private TimeSession session;
	private Timeline timeline;
	private Paint paint = new Paint();
	private int statusOffset = 100;

	public TimelineView(Context context) {
		super(context);
	}

	public TimelineView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void config() {
		paint.setStyle(Paint.Style.FILL);
	}

	public void setModels(TimeSession session, Timeline timeItem) {
		this.session = session;
		this.timeline = timeItem;
		invalidate();
	}

	@Override protected void onDraw(Canvas canvas) {
		if (timeline != null) {
			int width = canvas.getWidth() - statusOffset;

			long sessionDuration = session.getDuration();
			long sessionStartTime = session.getStartTime();
			long startOffset = 0;

			Out.pln();
			Out.pln();
			Out.pln();
			Out.pln("timeline-id", timeline.getId());

			LinkedList<TimePeriod> timePeriods = timeline.getPeriods();
			LinkedList<TimelineEvent> events = timeline.getEvents();

			for (TimePeriod period : timePeriods) {

				float start;//from 0 to 1
				float end;//from 0 to 1
				paint.setColor(period.getColor());

				if (period.getDuration() == -1) {
					continue;
				} else {
					start = period.getRelativeStartTime(sessionDuration, sessionStartTime);
					end = period.getRelativeEndTime(sessionDuration, sessionStartTime);
//					start = period.getRelativeStartTime(startOffset + startShift, sessionDuration);
//					end = period.getRelativeEndTime(startOffset + startShift, sessionDuration);
				}

				Out.pln();
				Out.pln("period", period.getId());
				Out.time("timeline-start", timeline.getStartTime());
				Out.time("timeline-end", timeline.getEndTime());
				Out.pln("session-duration", sessionDuration);
				Out.time("session-startTime", sessionStartTime);
				Out.pln("period-duration", period.getDuration());
				Out.time("period-start", period.getStartTime());
				Out.time("period-end", period.getEndTime());
				Out.pln("start-relative", start);
				Out.pln("end-relative", end);

				canvas.drawRect(statusOffset + width * start, 0, statusOffset + width * end, canvas.getHeight(), paint);

				startOffset += period.getDuration();
			}

			for (TimelineEvent event : events) {
				paint.setColor(Color.CYAN);
				float start = event.getRelativeTime(sessionDuration, sessionStartTime);
				canvas.drawRect(statusOffset + width * start, 0, statusOffset + width * start + 50, canvas.getHeight(), paint);
			}

			if (timeline.hasError()) {
				paint.setColor(Color.RED);
				canvas.drawRect(0, 0, statusOffset, canvas.getHeight(), paint);
			} else if (timeline.isEnded()) {
				paint.setColor(Color.GREEN);
				canvas.drawRect(0, 0, statusOffset, canvas.getHeight(), paint);
			} else {
				paint.setColor(Color.YELLOW);
				canvas.drawRect(0, 0, statusOffset, canvas.getHeight(), paint);
			}
		}
	}
}
