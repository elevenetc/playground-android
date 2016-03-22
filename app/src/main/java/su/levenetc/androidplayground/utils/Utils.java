package su.levenetc.androidplayground.utils;

import java.util.HashMap;

/**
 * Created by Eugene Levenetc on 22/03/2016.
 */
public class Utils {

	private static HashMap<String, Long> times = new HashMap<>();

	public static void startTime(String key) {
		times.put(key, System.nanoTime());
	}

	public static long endTime(String key) {
		if (times.containsKey(key)) return (System.nanoTime() - times.get(key)) / 1000000;
		else return 0;
	}
}
