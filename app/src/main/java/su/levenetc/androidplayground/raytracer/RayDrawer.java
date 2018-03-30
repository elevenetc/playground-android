package su.levenetc.androidplayground.raytracer;

import android.graphics.Canvas;
import android.graphics.Paint;

import su.levenetc.androidplayground.utils.Paints;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayDrawer {

    public static void draw(Light light, Canvas canvas){
        for (Ray ray : light.rays) {
            draw(ray, canvas);
        }
    }

    public static void draw(Ray ray, Canvas canvas) {
        if (!ray.lines.isEmpty()) {
            Line line = ray.lines.get(0);
            drawLine(canvas, line, Paints.Stroke.Yellow, true);
        } else {
            drawLine(canvas, ray.initVector, Paints.Stroke.Red, true);
        }
    }

    public static void draw(Scene scene, Canvas canvas){
        for (Shape object : scene.objects) {
            draw(object, canvas);
        }
    }

    public static void draw(Shape shape, Canvas canvas) {
        for (Line line : shape.lines) {
            drawLine(canvas, line, Paints.Stroke.GreenBold, true);
        }
    }

    private static void drawLine(Canvas canvas, Line line, Paint paint, boolean endings) {
        canvas.drawLine((float) line.x1, (float) line.y1, (float) line.x2, (float) line.y2, paint);

        if (line.normal != null) {
            canvas.drawLine((float) line.normal.x1, (float) line.normal.y1, (float) line.normal.x2, (float) line.normal.y2, Paints.Stroke.Red);
        }

        if (endings) {
            canvas.drawCircle((float) line.x1, (float) line.y1, 10f, Paints.Stroke.Yellow);
            canvas.drawCircle((float) line.x2, (float) line.y2, 5f, Paints.Stroke.Green);
        }
    }
}
