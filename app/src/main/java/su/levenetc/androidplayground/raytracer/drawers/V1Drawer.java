package su.levenetc.androidplayground.raytracer.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import su.levenetc.androidplayground.raytracer.Light;
import su.levenetc.androidplayground.raytracer.Line;
import su.levenetc.androidplayground.raytracer.Ray;
import su.levenetc.androidplayground.raytracer.Scene;

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
            //float d = ((float) i) / rays.size();
            //float decay = (float) Math.sin(d * Math.PI);
            //drawRay(rays.get(i), decay, canvas);
            drawRay(rays.get(i), 0, canvas);
        }
    }

    @Override
    public void draw(Scene scene, Canvas canvas) {

    }

    /**
     * @param decayVelocity 0.0 - 1.0
     */
    private void drawRay(Ray ray, float decayVelocity, Canvas canvas) {

        paint.setAlpha(255);

        for (Line line : ray.lines()) {

            double dx = line.x2 - line.x1;
            double dy = line.y2 - line.y1;
            double rayLen = Math.sqrt(dx * dx + dy * dy);

            double loc;//0.0 - 1.0
            double currentLen = 0;//px, 0.0 - rayLen
            double pxStep = 3;//px
            double stepMagnif = 1.005;//how fast distance between points are increased
            int step = 0;

            while (currentLen <= rayLen) {

                loc = currentLen / rayLen;

                double ratio = rayLen * loc / rayLen;
                double x = line.x1 + dx * ratio;
                double y = line.y1 + dy * ratio;

                drawStepArea(canvas, (float) pxStep, (float) x, (float) y, currentLen, decayVelocity);

                currentLen += pxStep;
                pxStep *= stepMagnif;
                step++;
            }
        }
    }

    private void drawStepArea(Canvas canvas, float pxStep,
                              float x, float y,
                              double currentLen,
                              double decayVelocity) {
        paint.setAlpha(getAlpha(currentLen, decayVelocity));
        float radius = pxStep / 2 + 10f;
        //canvas.drawCircle(x, y, radius, paint);
        canvas.drawRect(x, y, x + radius, y + radius, paint);
    }

    private int getAlpha(double currentLen, double decayVelocity) {
        double maxLen = 900;
        double decayStep = 220;
        double v = (currentLen + decayStep * decayVelocity) / maxLen;

        if (v > 1) v = 1;

        return (int) (50 * (1 - v));
    }
}
