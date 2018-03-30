package su.levenetc.androidplayground.raytracer;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayTracer {

    public static void trace(Light light, Scene scene) {
        for (Ray ray : light.rays) {
            trace(ray, scene);
        }
    }

    public static void trace(Ray ray, Scene scene) {
        ray.lines.clear();
        Point intersection = RayMath.getClosestWallIntersection(ray.initVector, scene);
        if (intersection != null) {
            ray.lines.add(new Line(ray.initVector, intersection.x, intersection.y));
        }
    }
}