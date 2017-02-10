package su.levenetc.androidplayground.models;

import android.graphics.Point;
import android.util.Log;
import com.google.android.gms.maps.Projection;
import su.levenetc.androidplayground.utils.MapUtils;

/**
 * Created by eugene.levenetc on 18/01/2017.
 */

public class MapVector {

	//TODO: simplify to line with only two points

	private final float[] path = new float[4];
	private final float[] emptyPath = new float[0];
	private Point screenSize;
	private MapLocation screenLocation;
	private double equatorLenght;

	private final MapLocation locA;
	private final MapLocation locB;
	private final MapLocation closerToEquator;
	private final MapLocation farFromEquator;
	private float angleWithEquator;

	public MapVector(MapLocation locA, MapLocation locB) {
		this.locA = locA;
		this.locB = locB;

		if (Math.abs(locA.geo.latitude) < Math.abs(locB.geo.latitude)) {
			closerToEquator = locA;
			farFromEquator = locB;
		} else {
			closerToEquator = locB;
			farFromEquator = locA;
		}

		final double angle = MapUtils.calcAngleWithEquator(locB, locA);
		if (angle == 0) {

		}

	}

	public void setScreenSize(Point screenSize) {
		this.screenSize = screenSize;
	}

	public void updateScreenCoordinates(Projection projection, double eqLenght) {
		this.equatorLenght = eqLenght;
		updateLoc(projection, locA);
		updateLoc(projection, locB);
		checkPoints();
	}

	private void updateLoc(Projection projection, MapLocation mapLocation) {
		final Point result = projection.toScreenLocation(mapLocation.geo);
		mapLocation.screen.set(result.x, result.y);
	}

	public float[] getPath() {

		path[0] = locA.screen.x;
		path[1] = locA.screen.y;
		path[2] = locB.screen.x;
		path[3] = locB.screen.y;

		return path;
	}

	private void checkPoints() {
		screenLocation = null;
		boolean aInScreen = isInScreen(locA);
		boolean bInScreen = isInScreen(locB);

		if (aInScreen) {
			screenLocation = locA;
		} else if (bInScreen) {
			screenLocation = locB;
		}

		if (screenLocation == null) {
			checkBothPoints();
		} else if (!aInScreen) {
			checkAPoint();
		} else if (!bInScreen) {
			checkBPoint();
		}
	}

	private void checkBothPoints() {

	}

	private void checkAPoint() {

	}

	private void checkBPoint() {

	}

	private boolean isInScreen(MapLocation location) {
		return location.screen.x >= 0 &&
				location.screen.y >= 0 &&
				location.screen.x <= screenSize.x &&
				location.screen.y <= screenSize.y;
	}

	private void recalculateScreenLocations() {

		MapLocation previous;
		MapLocation current = screenLocation;

		//towards
		while (current != null) {
			previous = current;
			current = current.next;
			if (current == null) continue;
			if (isInScreen(current)) continue;

			if (current.screen.x < previous.screen.x) {
				moveFromLeftToRight(previous, current);
			}
		}

		previous = null;
		current = screenLocation;

		//backwards
		while (current != null) {
			previous = current;
			current = current.previous;

			if (current == null) continue;
			if (isInScreen(current)) continue;

			if (current.screen.x > previous.screen.x) {
				moveFromRightToLeft(previous, current);
			}
		}
	}

	private void moveFromRightToLeft(MapLocation previous, MapLocation current) {
		Log.d("moveFromRightToLeft", "move " + current.name + " from right to left");
		final int currentDiffLoc = current.screen.x - previous.screen.x;
		current.screen.x = previous.screen.x - (int) (equatorLenght - currentDiffLoc);
	}

	private void moveFromLeftToRight(MapLocation previous, MapLocation current) {
		Log.d("moveFromLeftToRight", "move " + current.name + " from left  to right");
		final int currentDiffLoc = previous.screen.x + Math.abs(current.screen.x);
		current.screen.x = previous.screen.x + (int) (equatorLenght - currentDiffLoc);
	}
}
