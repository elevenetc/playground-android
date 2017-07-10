package su.levenetc.androidplayground.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import su.levenetc.androidplayground.exceptions.ObjectDeserializationException;
import su.levenetc.androidplayground.exceptions.ObjectSerialisationException;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class Utils {

	public static final Random RND = new Random();
	private static HashMap<String, DecimalFormat> decFormats = new HashMap<>();

	public static float randomInRange(float min, float max) {
		final float result = min + RND.nextFloat() * ((max - min));
		return result;
	}

	public static float randomInRangeSigned(float min, float max) {
		return randomInRange(min, max) * (RND.nextBoolean() ? 1f : -1f);
	}

	public static String formatFloat(float value, String format) {

		DecimalFormat decimalFormat;
		if (decFormats.containsKey(format)) {
			decimalFormat = decFormats.get(format);
		} else {
			decimalFormat = new DecimalFormat(format);
			decFormats.put(format, decimalFormat);
		}
		return decimalFormat.format(value);
	}

	public static int randomColor() {
		int result = Color.argb(255, RND.nextInt(256), RND.nextInt(256), RND.nextInt(256));
		return result;
	}

	public static int randomRed() {
		int result = Color.argb(255, RND.nextInt(256), 0, 0);
		return result;
	}

	/**
	 * @param color - Color.RED
	 * @param alpha - 0 to 255
	 * @return [255, 255, 255, 255]
	 */
	public static int[] intToRgba(int color, int alpha) {
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = (color >> 0) & 0xFF;
		return new int[]{r, g, b, alpha};
	}

	/**
	 * @param color - Color.RED
	 * @param alpha - 0.0 to 1.0
	 * @return [1.0, 1.0, 1.0, 1.0]
	 */
	public static float[] intToFloatRgba(int color, float alpha) {
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = (color >> 0) & 0xFF;
		return new float[]{r / 255f, g / 255f, b / 255f, alpha};
	}

	public static <T> T toObject(byte[] array) throws ObjectDeserializationException {
		ByteArrayInputStream bis = new ByteArrayInputStream(array);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			return (T) in.readObject();
		} catch (Exception e) {
			throw new ObjectDeserializationException(e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
	}


	public static byte[] toByteArray(Object object) throws ObjectSerialisationException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(object);
			out.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			throw new ObjectSerialisationException(e);
		} finally {
			try {
				bos.close();
			} catch (IOException ex) {
				// ignore close exception
			}
		}
	}

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

	public static Point getScreenSize(Activity context) {
		Display display = context.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}
}
