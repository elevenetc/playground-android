package su.levenetc.androidplayground.raytracer.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import su.levenetc.androidplayground.raytracer.Ray;
import su.levenetc.androidplayground.raytracer.RaySegment;
import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.lights.Light;

public class V1Drawer implements Drawer {

    private Paint paint = new Paint();

    public V1Drawer() {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Light light, Canvas canvas) {
        List<Ray> rays = light.rays();

        for (int i = 0; i < rays.size(); i++) {
            drawRay(rays.get(i), canvas);
        }
    }

    @Override
    public void draw(Scene scene, Canvas canvas) {

    }

    /**
     */
    private void drawRay(Ray ray, Canvas canvas) {

        paint.setAlpha(255);

        float spotRadius = 10f;//px
        double step = 5;//px

        for (RaySegment raySegment : ray.lines()) {

            double dx = raySegment.x2 - raySegment.x1;
            double dy = raySegment.y2 - raySegment.y1;
            double raySegmentLen = Math.sqrt(dx * dx + dy * dy);

            double loc;//0.0 - 1.0
            double currentLen = 0;//px, from 0.0 to raySegmentLen

            double fullRayLoc = raySegment.start;

            while (currentLen <= raySegmentLen) {

                loc = currentLen / raySegmentLen;

                double ratio = raySegmentLen * loc / raySegmentLen;
                double x = raySegment.x1 + dx * ratio;
                double y = raySegment.y1 + dy * ratio;

                drawStepArea(ray, canvas, (float) x, (float) y, fullRayLoc, spotRadius);

                fullRayLoc = raySegment.start + (raySegment.end - raySegment.start) * loc;
                currentLen += step;
            }
        }
    }

    private void drawStepArea(Ray ray,
                              Canvas canvas,
                              float x, float y,
                              double fullRayLoc,
                              float spotRadius) {
        paint.setAlpha(getAlpha(ray, fullRayLoc));
        canvas.drawRect(x, y, x + spotRadius, y + spotRadius, paint);
    }

    private int getAlpha(Ray ray, double fullRayLoc) {
        double v = fullRayLoc;
        if (v > 1) v = 1;
        return (int) (50 * (1 - v));
    }
}
