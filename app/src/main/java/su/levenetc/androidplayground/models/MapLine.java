package su.levenetc.androidplayground.models;

import android.graphics.Point;
import android.util.Log;
import com.google.android.gms.maps.Projection;

import java.util.List;

/**
 * Created by eugene.levenetc on 18/01/2017.
 */

public class MapLine {

	//TODO: simplify to line with only two points

	private List<MapLocation> locations;
	private float[] path;
	private float[] emptyPath = new float[0];
	private Point screenSize;
	private MapLocation screenLocation;

	public MapLine(List<MapLocation> locations) {
		this.locations = locations;
		path = new float[locations.size() * 2];
	}

	public void setScreenSize(Point screenSize) {
		this.screenSize = screenSize;
	}

	public void updateScreenCoordinates(Projection projection) {
		for (MapLocation mapLocation : locations) {
			final Point result = projection.toScreenLocation(mapLocation.geo);
			mapLocation.screen.set(result.x, result.y);
		}
		findScreenPoint();
	}

	public float[] getPath() {
		int index = 0;
		for (MapLocation location : locations) {
			path[index] = location.screen.x;
			path[index + 1] = location.screen.y;
			index += 2;
		}
		return path;
	}

	private void findScreenPoint() {
		screenLocation = null;
		boolean allInScreen = true;
		boolean allOutOfScreen = true;
		for (MapLocation location : locations) {
			if (isInScreen(location)) {
				allOutOfScreen = false;
				screenLocation = location;
				location.visible = true;
			} else {
				allInScreen = false;
				location.visible = false;
			}
		}

		if (!allInScreen) {
			//TODO: add case when all points are aligned correctly even when all out of screen
			recalculateScreenLocations();
		}

		if (allOutOfScreen) {
			checkAllOutOfScreen();
		}
	}

	private void checkAllOutOfScreen() {
		MapLocation previous = null;
		MapLocation current = locations.get(0);


		while (current != null) {

			previous = current;
			current = current.next;

			if (current == null) continue;

			if (current.screen.x < previous.screen.x) {
				fixRightPoint(previous, current);
			} else if (current.screen.x > previous.screen.x) {
				//fixLeftPoint(previous, current);
			}
		}
	}

	private boolean isInScreen(MapLocation location) {
		return location.screen.x >= 0 &&
				location.screen.y >= 0 &&
				location.screen.x <= screenSize.x &&
				location.screen.y <= screenSize.y;
	}

	private void recalculateScreenLocations() {

		MapLocation previous = null;
		MapLocation current = screenLocation;

		//TODO: add loops support

		//towards
		while (current != null) {
			previous = current;
			current = current.next;
			if (current == null) continue;
			if (isInScreen(current)) continue;

			if (current.screen.x < previous.screen.x) {
				fixRightPoint(previous, current);
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
				fixLeftPoint(previous, current);
			}
		}
	}

	private void fixLeftPoint(MapLocation previous, MapLocation current) {
		Log.d("fixLeftPoint", "move " + current.name + " from right to left");
		final int currentDiffLoc = current.screen.x - previous.screen.x;
		current.screen.x = previous.screen.x - currentDiffLoc;
	}

	private void fixRightPoint(MapLocation previous, MapLocation current) {
		Log.d("fixRightPoint", "move " + current.name + " from left  to right");
		final int currentDiffLoc = previous.screen.x + Math.abs(current.screen.x);
		current.screen.x = currentDiffLoc + previous.screen.x;
	}
}
