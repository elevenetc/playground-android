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

    private final Paint paint = new Paint();
    private final float spotRadius = 10f;//px
    /**
     * Spots overlap each other, so brightness(alpha) should be decreased
     */
    private static final float brightness = 1f;//.1f;
    /**
     * Spots overlap each other, so in the beginning size of spots should be smaller
     */
    private static final float minSpotSize = 0.7f;

    public V1Drawer() {
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Light light, Canvas canvas) {
        List<Ray> rays = light.rays();

        for (int i = 0; i < rays.size(); i++) drawRay(rays.get(i), canvas);
    }

    @Override
    public void draw(Scene scene, Canvas canvas) {

    }

    private void drawRay(Ray ray, Canvas canvas) {

        paint.setAlpha(255);
        paint.setColor(Color.RED);

        double step = 5;//px

        for (RaySegment raySegment : ray.reflectedOrRefracted()) {


            double dx = raySegment.x2 - raySegment.x1;
            double dy = raySegment.y2 - raySegment.y1;
            float raySegmentLen = (float) Math.sqrt(dx * dx + dy * dy);

            float loc;//0.0 - 1.0
            float currentLen = 0;//px, from 0.0 to raySegmentLen

            float startAlpha = raySegment.startAlpha;
            float endAlpha = raySegment.endAlpha;

            float fadeLoc = startAlpha;

            paint.setColor(raySegment.startColor);

            while (currentLen <= raySegmentLen) {

                loc = currentLen / raySegmentLen;

                double ratio = raySegmentLen * loc / raySegmentLen;
                double x = raySegment.x1 + dx * ratio;
                double y = raySegment.y1 + dy * ratio;

                drawStepArea(canvas, (float) x, (float) y, fadeLoc);


                fadeLoc = startAlpha + (endAlpha - startAlpha) * loc;
                currentLen += step;
            }
        }
    }


    /**
     * @param x       location of spot
     * @param y       location of spot
     * @param fadeLoc from 0 to infinity
     */
    private void drawStepArea(Canvas canvas,
                              float x, float y,
                              float fadeLoc) {
        //skip drawing transparent spots
        if (fadeLoc > 1) return;

        float sr = spotRadius * (fadeLoc + minSpotSize);

        paint.setAlpha(calculateAlpha(fadeLoc));
        canvas.drawRect(x, y, x + sr, y + sr, paint);
    }

    private int calculateAlpha(double fadeLoc) {
        double alpha = (1 - fadeLoc) * 255;
        alpha *= brightness;
        return (int) alpha;
    }
}
