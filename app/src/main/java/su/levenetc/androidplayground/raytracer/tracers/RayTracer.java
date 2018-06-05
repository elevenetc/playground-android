package su.levenetc.androidplayground.raytracer.tracers;

import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.lights.Light;

public interface RayTracer {
    void trace(Light light, Scene scene);
}
