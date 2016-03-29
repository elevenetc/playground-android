package su.levenetc.androidplayground.models;

import java.util.LinkedList;

/**
 * Created by Eugene Levenetc on 28/03/2016.
 */
public class TimeLine {

	private final LinkedList<TimeRange> timeRanges;
	private TimeLineSession session;
	private long timeInTimeline;

	public TimeLine() {
		timeRanges = new LinkedList<>();
	}

	public TimeLine(LinkedList<TimeRange> timeRanges) {
		this.timeRanges = timeRanges;
	}

	public LinkedList<TimeRange> getTimeRanges() {
		return timeRanges;
	}

	public TimeRange getFirst() {
		if (timeRanges.isEmpty()) return null;
		else return timeRanges.getFirst();
	}

	public TimeRange getLast() {
		if (timeRanges.isEmpty()) return null;
		else return timeRanges.getLast();
	}

	public long getDuration() {
		long duration = 0;
		for (TimeRange timeRange : timeRanges)
			duration += timeRange.getDuration();
		return duration;
	}

	public void add(TimeRange timeRange) {
		timeRanges.add(timeRange);
	}

	public void setSessionAndTime(TimeLineSession session, long timeInTimeline) {
		this.session = session;
		this.timeInTimeline = timeInTimeline;
	}

	public long getTimeInTimeline() {
		return timeInTimeline;
	}

	public TimeLineSession getSession() {
		return session;
	}

	public static class Builder {

		public static Builder create() {
			return new Builder();
		}

		LinkedList<TimeRange> timeRanges = new LinkedList<>();

		Builder() {

		}

		public Builder addRange(long duration, int color) {
			timeRanges.add(new TimeRange(duration, color));
			return this;
		}

		public Builder addRange(long duration) {
			timeRanges.add(new TimeRange(duration, 0));
			return this;
		}

		public TimeLine build() {
			return new TimeLine(timeRanges);
		}
	}

	@Override public String toString() {

		if (timeRanges.isEmpty()) {
			return "TimeLine{ empty }";
		}

		String ranges = "";

		for (TimeRange timeRange : timeRanges)
			ranges += "[duration " + timeRange.getDuration() + "]";
		ranges += "[total:" + getDuration() + "]";

		return "TimeLine{\n" +
				ranges +
				"\n}";
	}
}
