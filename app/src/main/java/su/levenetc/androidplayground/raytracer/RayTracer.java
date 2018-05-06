package su.levenetc.androidplayground.raytracer;

import android.util.Log;

import su.levenetc.androidplayground.raytracer.geometry.Point;
import su.levenetc.androidplayground.raytracer.lights.Light;

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
        ray.raySegments.clear();
        traceInternal(ray, ray.initVector, scene, 0);
    }

    private static void traceInternal(Ray ray, RaySegment initVector, Scene scene, double currentLength) {

        if (currentLength >= ray.length) return;

        RayMath.Intersection intersection = RayMath.getClosestIntersection(initVector, scene);

        if (intersection != null && intersection.point != null) {
            Point point = intersection.point;


            RaySegment newVector = new RaySegment(initVector, point.x, point.y);

            if (((int) newVector.length()) == 0) {
                Log.e("rayTracing", "collision" + new RaySegment(initVector, point.x, point.y).length());
                return;
            }

            //calc fading currentLength
            newVector.start = currentLength == 0 ? 0 : (currentLength / ray.length);
            double length = newVector.length();
            currentLength += length;
            newVector.end = currentLength / ray.length;

            ray.raySegments.add(newVector);

            double angle = RayMath.angleBetween(initVector, intersection.bound);
            RaySegment reflected = initVector.copy();
            reflected.translateTo(point);
            double newAngle = (angle - 180) * 2;
            RayMath.rotateSegment(reflected, newAngle);

            traceInternal(ray, reflected, scene, currentLength);
        }
    }
}