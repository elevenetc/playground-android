package su.levenetc.androidplayground;

import org.junit.Test;

import su.levenetc.androidplayground.models.TimeLine;
import su.levenetc.androidplayground.models.TimeLineSession;

import static junit.framework.Assert.assertEquals;

public class TimeLineTests {

	@Test public void startTimeTest() {
		TimeLineSession session = new TimeLineSession();
		TimeLine itemA = TimeLine.Builder.create().addRange(150).build();
		TimeLine itemB = TimeLine.Builder.create().addRange(100).build();
		session.add(itemA, itemB);

		assertEquals(150, session.getStartTimeOf(itemB));
	}

	@Test public void relativeTimes() {
		TimeLineSession session = new TimeLineSession();

		TimeLine timeLine = TimeLine.Builder.create()
				.addRange(100, 0)
				.addRange(100, 0)
				.build();

		session.add(timeLine);

		long sessionDuration = session.getDuration();

		assertEquals(0.0f, timeLine.getFirst().getRelativeStartTime(0, sessionDuration));
		assertEquals(0.5f, timeLine.getFirst().getRelativeEndTime(0, sessionDuration));

		assertEquals(0.5f, timeLine.getLast().getRelativeStartTime(100, sessionDuration));
		assertEquals(1.0f, timeLine.getLast().getRelativeEndTime(100, sessionDuration));
	}

	@Test public void sessionDuration() throws Exception {
		TimeLineSession session = new TimeLineSession();
		session.add(
				TimeLine.Builder.create()
						.addRange(100, 0)
						.addRange(100, 0)
						.build()
		);

		session.add(
				TimeLine.Builder.create()
						.addRange(100, 0)
						.addRange(100, 0)
						.build()
		);

		assertEquals(400, session.getDuration());
	}
}