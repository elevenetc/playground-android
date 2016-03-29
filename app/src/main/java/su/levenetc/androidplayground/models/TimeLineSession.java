package su.levenetc.androidplayground.models;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by Eugene Levenetc on 28/03/2016.
 */
public class TimeLineSession {

	LinkedList<TimeLine> items = new LinkedList<>();
	HashSet<TimeLine> set = new HashSet<>();

	public long getDuration() {

		if (items.isEmpty()) return 0;
		long duration = 0;
		for (TimeLine timeItem : items)
			duration += timeItem.getDuration();

		return duration;
	}

	public long getStartTimeOf(TimeLine item) {
		return item.getTimeInTimeline();
	}

	public void add(TimeLine item) {
		long time = 0;
		for (TimeLine timeItem : items) time += timeItem.getDuration();
		item.setSessionAndTime(this, time);
		items.add(item);
		set.add(item);
	}

	public void add(TimeLine... items) {
		for (TimeLine item : items) add(item);
	}

	@Override public String toString() {
		return "TimeLineSession{ duration:" + getDuration() + "}";
	}

	public int size() {
		return items.size();
	}

	public TimeLine getTimeLine(int index) {
		return items.get(index);
	}
}