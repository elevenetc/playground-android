package su.levenetc.androidplayground.raytracer.drawers;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;

import su.levenetc.androidplayground.raytracer.Ray;
import su.levenetc.androidplayground.raytracer.RaySegment;
import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.lights.Light;

public class SpotDrawer implements Drawer {

    private final Paint paint = new Paint();
    private float spotRadius;
    private float spotStep;

    public SpotDrawer() {
        this(10, 5);
        paint.setStyle(Paint.Style.FILL);
    }

    public SpotDrawer(float spotRadius, float spotStep) {
        paint.setStyle(Paint.Style.FILL);
        this.spotRadius = spotRadius;
        this.spotStep = spotStep;
    }

    @Override
    public void draw(Light light, Canvas canvas) {
        List<Ray> rays = light.rays();

        for (int i = 0; i < rays.size(); i++) drawRay(rays.get(i), light, canvas);
    }

    @Override
    public void draw(Scene scene, Canvas canvas) {

    }

    protected int calculateSpotAlpha(double fadeLoc, float brightness) {
        double alpha = (1 - fadeLoc) * 255;
        alpha *= brightness;
        return (int) alpha;
    }

    private void drawRay(Ray ray, Light light, Canvas canvas) {

        paint.setAlpha(255);

        for (RaySegment raySegment : ray.reflectedOrRefracted()) {


            double dx = raySegment.x2 - raySegment.x1;
            double dy = raySegment.y2 - raySegment.y1;
            float raySegmentLen = (float) Math.sqrt(dx * dx + dy * dy);

            float loc;//0.0 - 1.0
            float currentLen = 0;//px, from 0.0 to raySegmentLen

            float startAlpha = raySegment.startAlpha;
            float endAlpha = raySegment.endAlpha;

            float fadeLoc = startAlpha;

            paint.setColor(raySegment.color);

            while (currentLen <= raySegmentLen) {

                loc = currentLen / raySegmentLen;

                double ratio = raySegmentLen * loc / raySegmentLen;
                double x = raySegment.x1 + dx * ratio;
                double y = raySegment.y1 + dy * ratio;

                drawSpot(canvas, (float) x, (float) y, fadeLoc, light.brightness());


                fadeLoc = startAlpha + (endAlpha - startAlpha) * loc;
                currentLen += spotStep;
            }
        }
    }

    /**
     * @param x       location of spot
     * @param y       location of spot
     * @param fadeLoc from 0 to infinity.  values >1 will be treated as transparent
     */
    private void drawSpot(Canvas canvas,
                          float x, float y,
                          float fadeLoc,
                          float brightness) {
        //skip drawing transparent spots
        if (fadeLoc > 1) return;

        //float sr = spotRadius * (fadeLoc + minSpotSize);

        paint.setAlpha(calculateSpotAlpha(fadeLoc, brightness));
        canvas.drawRect(x, y, x + spotRadius, y + spotRadius, paint);
    }
}
