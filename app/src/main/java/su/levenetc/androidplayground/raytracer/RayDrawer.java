package su.levenetc.androidplayground.raytracer;

import android.graphics.Canvas;
import android.graphics.Paint;

import su.levenetc.androidplayground.utils.Paints;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayDrawer {

    public static void draw(Rect rect, Canvas canvas) {
        drawVector(canvas, rect.topBound, Paints.Stroke.GreenBold, false);
        drawVector(canvas, rect.leftBound, Paints.Stroke.GreenBold, false);
        drawVector(canvas, rect.rightBound, Paints.Stroke.GreenBold, false);
        drawVector(canvas, rect.bottomBound, Paints.Stroke.GreenBold, false);
    }

    public static void draw(Ray ray, Canvas canvas) {
        Vector vector = ray.vectors.get(0);
        drawVector(canvas, vector, Paints.Stroke.Yellow, true);
    }

    private static void drawVector(Canvas canvas, Vector vector, Paint paint, boolean bounds) {
        canvas.drawLine((float) vector.x1, (float) vector.y1, (float) vector.x2, (float) vector.y2, paint);

        if (vector.normal != null) {
            canvas.drawLine((float) vector.normal.x1, (float) vector.normal.y1, (float) vector.normal.x2, (float) vector.normal.y2, Paints.Stroke.Green);
            canvas.drawCircle((float) vector.normal.x2, (float) vector.normal.y2, 10, Paints.Stroke.Red);
        }

        if (bounds) {
            canvas.drawCircle((float) vector.x1, (float) vector.y1, 25f, Paints.Fill.Yellow);
            canvas.drawCircle((float) vector.x2, (float) vector.y2, 15f, Paints.Fill.Green);
        }
    }
}
