package su.levenetc.androidplayground.utils;

/**
 * Created by eugene.levenetc on 26/01/2017.
 */
public class MathUtils {
	public static double angleBetweenPoints(
			double centerX, double centerY,
			double aX, double aY,
			double bX, double bY
	) {
		return Math.toDegrees(Math.atan2(aX - centerX, aY - centerY) -
				Math.atan2(bX - centerX, bY - centerY));
	}
}
