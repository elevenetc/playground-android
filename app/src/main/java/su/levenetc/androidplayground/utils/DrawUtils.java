package su.levenetc.androidplayground.utils;

/**
 * Created by eugene.levenetc on 17/07/2017.
 */
public class DrawUtils {
	public static float[] quadraticBezier(
			float t,
			float fromX, float fromY,
			float toX, float toY,
			float controlX, float controlY) {
		float x = ((1 - t) * (1 - t) * fromX) + (2 * (1 - t) * t * controlX + t * t * toX);
		float y = ((1 - t) * (1 - t) * fromY) + (2 * (1 - t) * t * controlY + t * t * toY);
		return new float[]{x, y};
	}
}
