package su.levenetc.androidplayground.raytracer.drawers;

import android.graphics.Canvas;

import java.util.List;

import su.levenetc.androidplayground.raytracer.Light;
import su.levenetc.androidplayground.raytracer.Line;
import su.levenetc.androidplayground.raytracer.Ray;
import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.Shape;

public class V1Drawer implements Drawer {

    @Override
    public void draw(Light light, Canvas canvas) {
        List<Ray> rays = light.rays();

        for (Ray ray : rays) {
            drawRay(ray, canvas);
        }
    }

    @Override
    public void draw(Scene scene, Canvas canvas) {

    }

    private void drawRay(Ray ray, Canvas canvas){
        for (Line line : ray.lines()) {




        }
    }
}
