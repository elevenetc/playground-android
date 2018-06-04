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

public class V2Drawer implements Drawer {

    private Paint paint = new Paint();
    private float rayWidth;

    public V2Drawer(float rayWidth) {

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

    /**
     * @param brightness 0.0 - 1.0
     */
    public static int brightness(int color, float brightness) {
        int r = ((color & 0xff0000) >> 16);
        int g = ((color & 0xff00) >> 8);
        int b = (color & 0xff);

        r *= brightness;
        g *= brightness;
        b *= brightness;

        return r << 16 | g << 8 | b;
    }

    /**
     * @param alpha 0 - 255
     */
    public static int setAlpha(int rgb, int alpha) {
        return rgb | (alpha << 24);
    }

    private void drawRay(Ray ray, Light light, Canvas canvas) {

        for (RaySegment raySegment : ray.reflectedOrRefracted()) {
            paint.setColor(raySegment.color);
            paint.setStrokeWidth(rayWidth);
            float x1 = (float) raySegment.x1;
            float y1 = (float) raySegment.y1;
            float x2 = (float) raySegment.x2;
            float y2 = (float) raySegment.y2;
            float length = (float) raySegment.length();

            Color color = Color.valueOf(raySegment.color);

            int centerColor = Color.argb(color.alpha() * (1 - raySegment.startAlpha), color.red(), color.green(), color.blue());
            int edgeColor = Color.argb(color.alpha() * (1 - raySegment.endAlpha), color.red(), color.green(), color.blue());

            paint.setShader(new RadialGradient(
                    x1, y1,
                    length,
                    centerColor, edgeColor,
                    Shader.TileMode.CLAMP
            ));
            canvas.drawLine(x1, y1, x2, y2, paint);
        }
    }
}
