package su.levenetc.androidplayground.raytracer;

import android.util.Log;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayTracer {

    public static void trace(Light light, Scene scene) {
        long start = System.currentTimeMillis();
        for (Ray ray : light.rays) {
            trace(ray, scene);
        }
        Log.d("time-to-trace", String.valueOf(System.currentTimeMillis() - start));
    }

    public static void trace(Ray ray, Scene scene) {
        ray.lines.clear();
        traceInternal(ray, ray.initVector, scene, 0);
    }

    private static void traceInternal(Ray ray, Line initVector, Scene scene, double currentLength) {

        if (currentLength >= ray.length) return;

        RayMath.Intersection intersection = RayMath.getClosestWallIntersection(initVector, scene);

        if (intersection != null && intersection.point != null) {
            Point point = intersection.point;
            Line newVector = new Line(initVector, point.x, point.y);

            //calc fading currentLength
            newVector.start = currentLength == 0 ? 0 : (currentLength / ray.length);
            currentLength += newVector.length();
            newVector.end = currentLength / ray.length;

            ray.lines.add(newVector);

            double angle = RayMath.angleBetween(initVector, intersection.bound);
            Line reflected = initVector.copy();
            reflected.translateTo(point);
            double newAngle = (angle - 180) * 2;
            RayMath.rotateLine(reflected, newAngle);

            traceInternal(ray, reflected, scene, currentLength);
        }
    }
}