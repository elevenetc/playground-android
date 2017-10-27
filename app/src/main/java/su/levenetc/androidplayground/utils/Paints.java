package su.levenetc.androidplayground.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class Paints {

	public static class Stroke {
		public static Paint Blue = stroke(Color.BLUE);
		public static Paint White = stroke(Color.WHITE);
		public static Paint Green = stroke(Color.GREEN);
		public static Paint GreenBold = strokeBold(Color.GREEN);
		public static Paint Red = stroke(Color.RED);
	}

	public static class Fill {
		public static Paint Red = fill(Color.RED);
		public static Paint Grey = fill(Color.DKGRAY);
	}

	public static class Font {
		public static Paint Red_26 = font(Color.RED, 26);
		public static Paint Black_8 = font(Color.BLACK, 8);
		public static Paint Black_9 = font(Color.BLACK, 9);
		public static Paint Black_26 = font(Color.BLACK, 26);
		public static Paint Black_26_Alpha_50 = font(Color.BLACK, 26, 255 / 2);
	}

	static Paint strokeBold(int color) {
		final Paint result = stroke(color);
		result.setStrokeWidth(Values.DP_2);
		return result;
	}


	static Paint stroke(int color) {
		Paint result = new Paint();
		result.setStyle(Paint.Style.STROKE);
		result.setColor(color);
		return result;
	}

	static Paint fill(int color) {
		Paint result = new Paint();
		result.setStyle(Paint.Style.FILL);
		result.setColor(color);
		return result;
	}

	static Paint font(int color, float spSize) {
		return font(color, spSize, 255);
	}

	static Paint font(int color, float spSize, int alpha) {
		Paint result = new Paint();
		result.setTextSize(spSize * Resources.getSystem().getDisplayMetrics().scaledDensity);
		result.setStyle(Paint.Style.FILL);
		result.setColor(addAlpha(color, alpha));
		result.setAntiAlias(true);
		return result;
	}

	static int addAlpha(int color, int alpha) {
		return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
	}

}
