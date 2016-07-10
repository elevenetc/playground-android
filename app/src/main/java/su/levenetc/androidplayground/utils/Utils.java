package su.levenetc.androidplayground.utils;

import java.util.Arrays;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class Utils {
	public static String getFilledString(int length, char c) {
		if (length < 0) throw new IllegalArgumentException("length must be >= 0");
		if (length > 0) {
			char[] array = new char[length];
			Arrays.fill(array, c);
			return new String(array);
		}
		return "";
	}
}
