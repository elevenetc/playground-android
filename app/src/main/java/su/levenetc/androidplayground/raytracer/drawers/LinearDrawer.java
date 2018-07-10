package su.levenetc.androidplayground.raytracer.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import su.levenetc.androidplayground.raytracer.Ray;
import su.levenetc.androidplayground.raytracer.RaySegment;
import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.lights.Light;

public class LinearDrawer implements Drawer {

    private Paint paint = new Paint();
    private float rayWidth;

    public LinearDrawer(float rayWidth) {

        this.rayWidth = rayWidth;
    }

    @Override
    public void draw(Light light, Canvas canvas) {
        for (Ray ray : light.rays()) {
            drawRay(ray, light, canvas);
        }
    }

    @Override
    public void draw(Scene scene, Canvas canvas) {

    }

    private void drawRay(Ray ray, Light light, Canvas canvas) {

        for (RaySegment raySegment : ray.reflectedOrRefracted()) {
            paint.setColor(raySegment.color);
            paint.setStrokeWidth(rayWidth);
            float x1 = (float) raySegment.x1;
            float y1 = (float) raySegment.y1;
            float x2 = (float) raySegment.x2;
            float y2 = (float) raySegment.y2;
            float length;

            if (raySegment.endAlpha > 1) {
                length = (float) raySegment.length() / raySegment.endAlpha;
            } else {
                length = (float) raySegment.length();
            }


            Color color = Color.valueOf(raySegment.color);

            int startColor = Color.argb(fixAlpha(color.alpha(), raySegment.startAlpha), color.red(), color.green(), color.blue());
            int endColor = Color.argb(fixAlpha(color.alpha(), raySegment.endAlpha), color.red(), color.green(), color.blue());

            RadialGradient shader = new RadialGradient(
                    x1, y1,
                    length,
                    startColor, endColor,
                    Shader.TileMode.CLAMP
            );

            paint.setAlpha((int) (light.brightness() * 255));
            paint.setShader(shader);
            canvas.drawLine(x1, y1, x2, y2, paint);
        }
    }

    private float fixAlpha(float alpha, float ratio) {
        if (ratio == 0) return 1;
        else if (ratio >= 1) return 0;
        else return alpha * (1 - ratio);
    }


}
