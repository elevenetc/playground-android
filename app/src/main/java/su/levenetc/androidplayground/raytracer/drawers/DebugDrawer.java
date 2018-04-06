package su.levenetc.androidplayground.raytracer.drawers;

import android.graphics.Canvas;
import android.graphics.Paint;

import su.levenetc.androidplayground.raytracer.Light;
import su.levenetc.androidplayground.raytracer.Line;
import su.levenetc.androidplayground.raytracer.Ray;
import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.Shape;
import su.levenetc.androidplayground.utils.Paints;

public class DebugDrawer implements Drawer {

    @Override
    public void draw(Light light, Canvas canvas) {
        for (Ray ray : light.rays()) {
            draw(ray, canvas);
        }
    }

    @Override
    public void draw(Scene scene, Canvas canvas) {
        for (Shape object : scene.objects()) {
            draw(object, canvas);
        }
    }

    public void draw(Ray ray, Canvas canvas) {
        if (!ray.lines().isEmpty()) {

            for (Line line : ray.lines()) {
                drawLine(canvas, line, Paints.Stroke.Yellow, true);
            }

        } else {
            drawLine(canvas, ray.initVector(), Paints.Stroke.Red, true);
        }
    }

    public void draw(Shape shape, Canvas canvas) {
        for (Line line : shape.lines()) {
            drawLine(canvas, line, Paints.Stroke.GreenBold, true);
        }
    }

    private static void drawLine(Canvas canvas, Line line, Paint paint, boolean endings) {
        canvas.drawLine((float) line.x1, (float) line.y1, (float) line.x2, (float) line.y2, paint);

        Line normal = line.normal();
        if (normal != null) {
            canvas.drawLine((float) normal.x1, (float) normal.y1, (float) normal.x2, (float) normal.y2, Paints.Stroke.Red);
        }

        if (endings) {
            canvas.drawCircle((float) line.x1, (float) line.y1, 10f, Paints.Stroke.Yellow);
            canvas.drawCircle((float) line.x2, (float) line.y2, 5f, Paints.Stroke.Green);
        }
    }
}
