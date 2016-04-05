package su.levenetc.androidplayground.models;

/**
 * Created by Eugene Levenetc on 05/04/2016.
 */
public class TimelineEvent {

	private static final int TYPE_ERROR = 0;
	private static final int TYPE_RETRY = 1;
	private long time;
	private int type = -1;

	public static TimelineEvent retry() {
		return new TimelineEvent(TYPE_RETRY);
	}

	public static TimelineEvent error() {
		return new TimelineEvent(TYPE_ERROR);
	}

	private TimelineEvent(int type) {
		this.type = type;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isError() {
		return type == TYPE_ERROR;
	}

	public float getRelativeTime(long sessionDuration, long sessionStartTime) {
		float result = (time - sessionStartTime) / (float) sessionDuration;
		return result;

	}
}