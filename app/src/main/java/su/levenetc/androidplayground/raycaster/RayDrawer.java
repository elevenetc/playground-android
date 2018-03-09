package su.levenetc.androidplayground.raycaster;

import android.graphics.Canvas;

import su.levenetc.androidplayground.utils.Paints;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayDrawer {
    
    public static void draw(Ray ray, Canvas canvas) {
        Vector vector = ray.vectors.get(0);
        canvas.drawLine((float) vector.x1, (float) vector.y1, (float) vector.x2, (float) vector.y2, Paints.Stroke.Green);
    }
}
