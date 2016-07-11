package su.levenetc.androidplayground.utils;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class ThreadUtils {
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			//ignore
		}
	}
}
