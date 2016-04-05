package su.levenetc.androidplayground;

import android.graphics.Color;

import org.junit.Test;

import su.levenetc.androidplayground.models.TimeSession;
import su.levenetc.androidplayground.models.TimePeriod;
import su.levenetc.androidplayground.models.Timeline;

import static junit.framework.Assert.assertEquals;

public class TimeLineTests {

//	@Test public void relativeTimes() {
//		TimeSession session = new TimeSession();
//
//		Timeline timeLine = Timeline.Builder.create()
//				.addPeriod(100, 0)
//				.addPeriod(100, 0)
//				.build();
//
//		session.add(timeLine);
//
//		long sessionDuration = session.getDuration();
//
//		assertEquals(0.0f, timeLine.getFirst().getRelativeStartTime(0, sessionDuration));
//		assertEquals(0.5f, timeLine.getFirst().getRelativeEndTime(0, sessionDuration));
//
//		assertEquals(0.5f, timeLine.getLast().getRelativeStartTime(100, sessionDuration));
//		assertEquals(1.0f, timeLine.getLast().getRelativeEndTime(100, sessionDuration));
//	}

	@Test public void relativeTimes() {
		TimeSession session = TimeSession.create();

		Timeline timeline = session.createTimeline(0);
		TimePeriod period00 = addPeriod(session, timeline, 200, 300);
		TimePeriod period01 = addPeriod(session, timeline, 300, 400);

		long sessionDuration = session.getDuration();

		float start00 = period00.getRelativeStartTime(sessionDuration);
		float end00 = period00.getRelativeEndTime(sessionDuration);
		
		float start01 = period01.getRelativeStartTime(sessionDuration);
		float end01 = period01.getRelativeEndTime(sessionDuration);
		if (start01 == 0) {

		}
	}

	@Test public void sessionDuration() throws Exception {
		TimeSession session = TimeSession.create();
		Timeline timeline = session.createTimeline(100);

		addPeriod(session, timeline, 200, 300);
		addPeriod(session, timeline, 300, 400);

		assertEquals(200, session.getDuration());
	}

	private TimePeriod addPeriod(TimeSession session, Timeline timeline, int startPeriodTime, int endPeriodTime) {
		TimePeriod period = TimePeriod.create(Color.RED);
		session.startPeriod(timeline, period, startPeriodTime);
		session.endPeriod(period, endPeriodTime);
		return period;
	}
}