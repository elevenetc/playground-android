package su.levenetc.androidplayground.raytracer.drawers;

import android.graphics.Canvas;

import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.lights.Light;

public interface Drawer {
    void draw(Light light, Canvas canvas);

    void draw(Scene scene, Canvas canvas);
}
