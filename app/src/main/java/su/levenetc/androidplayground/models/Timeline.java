package su.levenetc.androidplayground.models;

import java.util.LinkedList;

/**
 * Created by Eugene Levenetc on 28/03/2016.
 */
public class Timeline {

	private final LinkedList<TimePeriod> periods;
	private final LinkedList<TimelineEvent> events = new LinkedList<>();
	private long startTime;
	private long endTime = -1;
	private String id;
	private boolean hasError;

	public Timeline(String id, long startTime, LinkedList<TimePeriod> periods) {
		this.startTime = startTime;
		this.periods = periods;
		this.id = id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LinkedList<TimePeriod> getPeriods() {
		return periods;
	}

	public TimePeriod getFirst() {
		if (periods.isEmpty()) return null;
		else return periods.getFirst();
	}

	public TimePeriod getLast() {
		if (periods.isEmpty()) return null;
		else return periods.getLast();
	}

	public long getDuration() {
		long duration = 0;
		for (TimePeriod timePeriod : periods) duration += timePeriod.getDuration();
		return duration;
	}

	public void add(TimePeriod timePeriod) {
		periods.add(timePeriod);
	}

	public long getTimeInTimeline() {
		return startTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public String getId() {
		return id;
	}

	public boolean isEnded() {
		return endTime != -1;
	}

	public void addEvent(TimelineEvent event) {
		hasError = event.isError();
		events.add(event);
	}

	public LinkedList<TimelineEvent> getEvents() {
		return events;
	}

	public boolean hasError() {
		return hasError;
	}

	public static class Builder {

		private long startTime;
		private String id;

		public static Builder create(String id, long startTime) {
			Builder builder = new Builder();
			builder.startTime = startTime;
			builder.id = id;
			return builder;
		}

		LinkedList<TimePeriod> timePeriods = new LinkedList<>();

		private Builder() {

		}

		public Builder addPeriod(long startTime, long endTime) {
			TimePeriod period = TimePeriod.create(0);
			period.startAt(startTime);
			period.endAt(endTime);
			timePeriods.add(period);
			return this;
		}

		public Timeline build() {
			return new Timeline(id, startTime, timePeriods);
		}
	}

	@Override public String toString() {

		if (periods.isEmpty()) {
			return "Timeline{ empty }";
		}

		String ranges = "";

		for (TimePeriod timePeriod : periods)
			ranges += "[duration " + timePeriod.getDuration() + "]";
		ranges += "[total:" + getDuration() + "]";

		return "Timeline{\n" +
				ranges +
				"\n}";
	}
}
