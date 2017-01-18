package su.levenetc.androidplayground.models;

import android.graphics.Point;

import com.google.android.gms.maps.Projection;

import java.util.List;

/**
 * Created by eugene.levenetc on 18/01/2017.
 */

public class MapPath {

    private List<MapLocation> locations;
    private float[] path;

    public MapPath(List<MapLocation> locations) {
        this.locations = locations;
        path = new float[locations.size() * 2];
    }

    public void updateScreenCoordinates(Projection projection) {
        for (MapLocation mapLocation : locations) {
            final Point result = projection.toScreenLocation(mapLocation.geo);
            mapLocation.screen.set(result.x, result.y);
        }
    }

    public float[] getPath() {
        int index = 0;
        for (int i = 0; i < locations.size(); i++) {
            path[index] = locations.get(i).screen.x;
            path[index + 1] = locations.get(i).screen.y;
            index += 2;
        }
        return path;
    }
}
