package su.levenetc.androidplayground.raytracer;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayTracer {
    public static void trace(Ray ray, EnvModel model) {
        Point intersection = RayMath.getClosestWallIntersection(ray.initVector, model);
        ray.vectors.add(new Vector(ray.initVector, intersection.x, intersection.y));
    }
}