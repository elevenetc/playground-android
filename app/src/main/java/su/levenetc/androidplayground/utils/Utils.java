package su.levenetc.androidplayground.utils;

import android.content.Context;
import android.util.TypedValue;

import java.util.Arrays;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class Utils {

	public static float getSp(float sp, Context context) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sp, context.getResources().getDisplayMetrics());
	}

	public static String getFilledString(int length, char c) {
		if (length < 0) throw new IllegalArgumentException("length must be >= 0");
		if (length > 0) {
			char[] array = new char[length];
			Arrays.fill(array, c);
			return new String(array);
		}
		return "";
	}

	public static String fixLength(String string, int max, char extraChar) {
		if (string.length() < max) {
			int diff = max - string.length();
			string += getFilledString(diff, extraChar);
		} else if (string.length() > max) {
			string = string.substring(0, max);
		}
		return string;
	}
}
