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
        traceInternal(ray, ray.initVector, scene, 5);
    }

    private static void traceInternal(Ray ray, Line initVector, Scene scene, int count) {

        if (count == 0) return;

        RayMath.Intersection intersection = RayMath.getClosestWallIntersection(initVector, scene);

        if (intersection != null && intersection.point != null) {
            Point point = intersection.point;
            Line newVector = new Line(initVector, point.x, point.y);
            ray.lines.add(newVector);


            double angle = RayMath.angleBetween(initVector, intersection.bound);
            Line reflected = initVector.copy();
            reflected.translateTo(point);
            double newAngle = (angle - 180) * 2;
            RayMath.rotateLine(reflected, newAngle);

            traceInternal(ray, reflected, scene, --count);
        }
    }
}