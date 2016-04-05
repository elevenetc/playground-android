package su.levenetc.androidplayground.models;

import java.util.LinkedList;

import su.levenetc.androidplayground.utils.Out;
import su.levenetc.androidplayground.utils.SessionBus;

/**
 * Created by Eugene Levenetc on 28/03/2016.
 */
public class TimeSession {

	private LinkedList<Timeline> timelines = new LinkedList<>();
	private SessionBus bus;
	private TimeProvider timeProvider;
	private long startTime;
	private boolean isAllEnded;

	public long getDuration() {

		if (timelines.isEmpty()) return 0;

		if (isAllEnded) {
			long max = 0;
			for (Timeline timeItem : timelines) {
				long currentMax = timeItem.getDuration();
				if (currentMax > max) max = currentMax;
			}

			return max;
		} else {
			return timeProvider.currentTime() - startTime;
		}
	}

	public long getStartTime() {
		return startTime;
	}

	public long getStartTimeOf(Timeline item) {
		return item.getStartTime();
	}

	public Timeline createTimeline(String id) {
		isAllEnded = false;
		return createTimeline(id, getTimeProvider().currentTime());
	}

	public Timeline createTimeline(String id, long creationTime) {
		Out.pln("created-timeline", id);
		Timeline timeline = Timeline.Builder.create(id, creationTime).build();
		add(timeline);
		return timeline;
	}

	public void add(Timeline item) {
		if (timelines.isEmpty()) startTime = timeProvider.currentTime();
		timelines.add(item);
		if (bus != null) bus.notifyAddedTimeLine();
	}

	public void addEvent(Timeline timeline, TimelineEvent event) {
		event.setTime(timeProvider.currentTime());
		timeline.addEvent(event);
		if (bus != null) bus.notifyAddedEvent();
	}

	public void startPeriod(Timeline timeline, TimePeriod period) {
		startPeriod(timeline, period, getTimeProvider().currentTime());
	}

	public void startPeriod(Timeline timeline, TimePeriod period, long currentTime) {
		period.startAt(currentTime);
		period.setTimeline(timeline);
		timeline.add(period);
		if (bus != null) bus.notifyPeriodStarted();
	}

	public void endPeriod(TimePeriod period) {
		endPeriod(period, getTimeProvider().currentTime());
	}

	public void endPeriod(TimePeriod period, long currentTime) {
		period.endAt(currentTime);
		if (bus != null) bus.notifyPeriodEnded();
	}

	public void endTimeLine(Timeline timeline) {
		endTimeLine(timeline, getTimeProvider().currentTime());
	}

	public void endTimeLine(Timeline timeline, long currentTime) {
		timeline.setEndTime(currentTime);
		for (Timeline t : timelines) {
			if (!t.isEnded()) isAllEnded = false;
			return;
		}
		isAllEnded = true;
	}

	public void add(Timeline... items) {
		for (Timeline item : items) add(item);
	}

	@Override public String toString() {
		return "TimeSession{ duration:" + getDuration() + "}";
	}

	public int size() {
		return timelines.size();
	}

	public Timeline getTimeLine(int index) {
		return timelines.get(index);
	}

	public void setBus(SessionBus bus) {
		this.bus = bus;
	}

	private TimeProvider getTimeProvider() {
		if (timeProvider == null) timeProvider = new TimeProvider() {
			@Override public long currentTime() {
				return System.nanoTime() / 1000000;
//				return System.currentTimeMillis();
			}
		};
		return timeProvider;
	}

	public static TimeSession create() {
		return new TimeSession();
	}

	public interface TimeProvider {
		long currentTime();
	}
}