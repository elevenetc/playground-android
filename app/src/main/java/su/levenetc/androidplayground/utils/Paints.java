package su.levenetc.androidplayground.utils;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class Paints {

	public static class Stroke {
		public static Paint Blue = new Paint();

		static {
			Blue.setStyle(Paint.Style.STROKE);
			Blue.setColor(Color.BLUE);
		}
	}

	public static class Fill {
		public static Paint Red = new Paint();

		static {
			Red.setColor(Color.RED);
			Red.setStyle(Paint.Style.FILL);
		}
	}
}
