package su.levenetc.androidplayground.utils;

import android.content.Context;
import android.util.TypedValue;
import su.levenetc.androidplayground.exceptions.ObjectDeserializationException;
import su.levenetc.androidplayground.exceptions.ObjectSerialisationException;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class Utils {

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
		return new float[]{r / 255, g / 255, b / 255, alpha};
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
}
