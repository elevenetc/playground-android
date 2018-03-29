package su.levenetc.androidplayground.raytracer;

import android.graphics.Canvas;
import android.graphics.Paint;

import su.levenetc.androidplayground.utils.Paints;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayDrawer {

    public static void draw(Rect rect, Canvas canvas) {
        drawLine(canvas, rect.topBound, Paints.Stroke.GreenBold, false);
        drawLine(canvas, rect.leftBound, Paints.Stroke.GreenBold, false);
        drawLine(canvas, rect.rightBound, Paints.Stroke.GreenBold, false);
        drawLine(canvas, rect.bottomBound, Paints.Stroke.GreenBold, false);
    }

    public static void draw(Ray ray, Canvas canvas) {
        Line line = ray.lines.get(0);
        drawLine(canvas, line, Paints.Stroke.Yellow, true);
    }

    public static void draw(Shape shape, Canvas canvas) {
        for (Line line : shape.lines) {
            drawLine(canvas, line, Paints.Stroke.GreenBold, true);
        }
    }

    private static void drawLine(Canvas canvas, Line line, Paint paint, boolean bounds) {
        canvas.drawLine((float) line.x1, (float) line.y1, (float) line.x2, (float) line.y2, paint);

        if (line.normal != null) {
            canvas.drawLine((float) line.normal.x1, (float) line.normal.y1, (float) line.normal.x2, (float) line.normal.y2, Paints.Stroke.Green);
            canvas.drawCircle((float) line.normal.x2, (float) line.normal.y2, 10, Paints.Stroke.Red);
        }

        if (bounds) {
            canvas.drawCircle((float) line.x1, (float) line.y1, 25f, Paints.Fill.Yellow);
            canvas.drawCircle((float) line.x2, (float) line.y2, 15f, Paints.Fill.Green);
        }
    }
}
