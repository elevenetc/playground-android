package su.levenetc.androidplayground.utils;

/**
 * Created by eugene.levenetc on 26/01/2017.
 */
public class MathUtils {

	/**
	 * Returns value from 0.0 to 1.0
	 *
	 * @param val from 0 to {@code max}
	 * @param max any value
	 * @return
	 */
	public static float sinValue(float val, float max) {
		return (float) Math.sin((Math.PI * val) / max);
	}

	public static double angleBetweenPoints(
			double centerX, double centerY,
			double aX, double aY,
			double bX, double bY
	) {
		return Math.toDegrees(Math.atan2(aX - centerX, aY - centerY) -
				Math.atan2(bX - centerX, bY - centerY));
	}
}