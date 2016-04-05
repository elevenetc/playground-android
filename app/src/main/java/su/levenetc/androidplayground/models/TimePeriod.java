package su.levenetc.androidplayground.models;

import su.levenetc.androidplayground.utils.Out;

/**
 * Created by Eugene Levenetc on 28/03/2016.
 */
public class TimePeriod {

	private long startTime = -1;
	private long endTime = -1;
	private final int color;
	private final String id;
	private Timeline timeline;

	public static TimePeriod create(int color) {
		return new TimePeriod(null, color);
	}

	public static TimePeriod create(String id, int color) {
		return new TimePeriod(id, color);
	}

	private TimePeriod(String id, int color) {
		this.color = color;
		this.id = id;
	}

	public long getEndTime() {
		return endTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getDuration() {
		if (endTime == -1) return -1;
		else return endTime - startTime;
	}

	public int getColor() {
		return color;
	}

	public float getRelativeStartTime(long sessionDuration, long sessionStartTime) {
		float result = (startTime - sessionStartTime) / (float) sessionDuration;
		return result;
	}

	public float getRelativeEndTime(long sessionDuration, long sessionStartTime) {
		float result = (endTime - sessionStartTime) / (float) sessionDuration;
		return result;
	}

	public void endAt(long time) {
		this.endTime = time;
		Out.time("end:" + id, time);
	}

	public void startAt(long time) {
		startTime = time;
		Out.time("start:" + id, time);
	}

	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}

	public String getId() {
		return id;
	}
}