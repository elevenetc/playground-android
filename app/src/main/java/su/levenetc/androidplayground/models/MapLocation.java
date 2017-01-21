package su.levenetc.androidplayground.models;

import android.graphics.Point;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by eugene.levenetc on 18/01/2017.
 */

public class MapLocation {

	public LatLng geo;
	public Point screen = new Point();
	public int latIndex;
	public boolean visible;
	public MapLocation next;
	public MapLocation previous;
	public String name;

	public MapLocation(LatLng geo) {
		this.geo = geo;
	}

	@Override public String toString() {
		return "MapLocation{" +
				"screen=" + screen.x +
				", name='" + name + '\'' +
				'}';
	}
}