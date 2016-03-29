package su.levenetc.androidplayground.models;

/**
 * Created by Eugene Levenetc on 28/03/2016.
 */
public class TimeRange {

	private final long duration;
	private final int color;

	public TimeRange(long duration, int color) {
		this.duration = duration;
		this.color = color;
	}

	public long getDuration() {
		return duration;
	}

	public int getColor() {
		return color;
	}

	public float getRelativeStartTime(long startOffset, long sessionTime) {
		return (startOffset) / (float) sessionTime;
	}

	public float getRelativeEndTime(long startOffset, long sessionTime) {
		return (getDuration() + startOffset) / (float) sessionTime;
	}
}