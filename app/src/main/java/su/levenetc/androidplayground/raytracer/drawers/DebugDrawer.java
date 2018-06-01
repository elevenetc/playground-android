package su.levenetc.androidplayground.raytracer.drawers;

import android.graphics.Canvas;
import android.graphics.Paint;

import su.levenetc.androidplayground.raytracer.Ray;
import su.levenetc.androidplayground.raytracer.RaySegment;
import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.edges.Edge;
import su.levenetc.androidplayground.raytracer.geometry.Segment;
import su.levenetc.androidplayground.raytracer.lights.Light;
import su.levenetc.androidplayground.raytracer.shapes.Shape;
import su.levenetc.androidplayground.utils.Paints;

public class DebugDrawer implements Drawer {

    @Override
    public void draw(Light light, Canvas canvas) {
        for (Ray ray : light.rays()) {
            draw(ray, canvas);
        }
    }

    private static void drawRaySegment(Canvas canvas, RaySegment raySegment, Paint paint, boolean endings) {
        drawLine(canvas, raySegment, paint);
        if (endings) drawEndings(canvas, raySegment);
    }

    public void draw(Shape shape, Canvas canvas) {
        for (Edge edge : shape.edges())
            drawEdge(canvas, edge, Paints.Stroke.GreenBold, true);

    }

    private static void drawNormal(Canvas canvas, Segment normal) {
        if (normal != null)
            canvas.drawLine((float) normal.x1, (float) normal.y1, (float) normal.x2, (float) normal.y2, Paints.Stroke.Red);
    }

    private static void drawLine(Canvas canvas, Segment edge, Paint paint) {
        canvas.drawLine((float) edge.x1, (float) edge.y1, (float) edge.x2, (float) edge.y2, paint);
    }

    private static void drawEndings(Canvas canvas, Segment raySegment) {
        canvas.drawCircle((float) raySegment.x1, (float) raySegment.y1, 10f, Paints.Stroke.Yellow);
        canvas.drawCircle((float) raySegment.x2, (float) raySegment.y2, 5f, Paints.Stroke.Green);
    }

    @Override
    public void draw(Scene scene, Canvas canvas) {
        for (Shape object : scene.objects()) draw(object, canvas);
    }

    public void draw(Ray ray, Canvas canvas) {
        if (!ray.reflectedOrRefracted().isEmpty()) {
            for (RaySegment raySegment : ray.reflectedOrRefracted())
                drawRaySegment(canvas, raySegment, Paints.Stroke.Yellow, true);
        } else {
            drawRaySegment(canvas, ray.initVector(), Paints.Stroke.Red, true);
        }
    }

    private static void drawEdge(Canvas canvas, Edge edge, Paint paint, boolean endings) {
        drawLine(canvas, edge, paint);

        if (edge.leftSide().hasNormal()) {
            drawNormal(canvas, edge.leftSide().normal());
        }

        if (edge.rightSide().hasNormal()) {
            drawNormal(canvas, edge.rightSide().normal());
        }


        if (endings) drawEndings(canvas, edge);
    }
}
