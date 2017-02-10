package su.levenetc.androidplayground.utils;

import su.levenetc.androidplayground.models.MapLocation;

/**
 * Created by eugene.levenetc on 24/01/2017.
 */

public class MapUtils {

	public static double calcAngleWithEquator(MapLocation locA, MapLocation locB) {
		double latA = locA.geo.latitude;
		double lonA = locA.geo.longitude / 2;
		double latB = locB.geo.latitude;
		double lonB = locB.geo.longitude / 2;
		double latC = latB;
		double lonC = lonA;

		return MathUtils.angleBetweenPoints(
				lonB, latB,
				lonA, latA,
				lonC, latC
		);
	}

	public static double equatorLength(float dp256, float zoom) {
		return dp256 * Math.pow(2, zoom);
	}
}
