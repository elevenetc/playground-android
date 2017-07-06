package su.levenetc.androidplayground.utils;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class Paints {

	public static class Stroke {
		public static Paint Blue = stroke(Color.BLUE);
		public static Paint White = stroke(Color.WHITE);
		public static Paint Red = stroke(Color.RED);
	}

	public static class Fill {
		public static Paint Red = fill(Color.RED);
		public static Paint Grey = fill(Color.DKGRAY);
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

}
