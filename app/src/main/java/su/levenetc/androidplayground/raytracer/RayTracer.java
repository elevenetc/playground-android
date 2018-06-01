package su.levenetc.androidplayground.raytracer;

import android.support.annotation.NonNull;
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
        ray.reset();
        traceInternal(ray, ray.initSegment, scene, 0, ray.initSegment.color);
    }

    private static void traceInternal(Ray ray, RaySegment initSegment, Scene scene, double currentLength, int prevColor) {

        if (currentLength >= ray.length) return;

        RayMath.Intersection intersection = RayMath.getClosestIntersection(initSegment, scene);

        if (intersection.exists()) {
            Point point = intersection.point;

            RaySegment newSegment = new RaySegment(initSegment, point.x, point.y);
            newSegment.color = prevColor;

            if (((int) newSegment.length()) == 0) {
                //stop tracing on collision case
                return;
            }

            //calc fading and rest length
            currentLength = setFading(ray, currentLength, newSegment);


            ray.reflectedOrRefracted().add(newSegment);

            //continue tracing
            traceInternal(ray, rotateInitVector(initSegment, intersection), scene, currentLength, intersection.hasOutColor ? intersection.outColor : prevColor);
        } else if (currentLength < ray.length) {//no intersection but light still has energy

            RaySegment newSegment = new RaySegment(initSegment);
            newSegment.color = prevColor;

            setFading(ray, currentLength, newSegment);

            ray.reflectedOrRefracted().add(newSegment);
        }
    }

    private static double setFading(Ray ray, double currentLength, RaySegment newSegment) {
        newSegment.startAlpha = currentLength == 0 ? 0 : (float) (currentLength / ray.length);

        double length = newSegment.length();
        currentLength += length;
        newSegment.endAlpha = (float) ((currentLength / ray.length));
        return currentLength;
    }

    @NonNull
    private static RaySegment rotateInitVector(RaySegment initVector, RayMath.Intersection intersection) {

        double inputAngle = RayMath.angleBetween(initVector, intersection.edge);
        Point point = intersection.point;
        RaySegment reflected = initVector.copy();

        reflected.translateTo(point);

        double outputAngle;

        if (intersection.side.isTransparent()) {
            double dot = RayMath.dotProduct(initVector.normalized(), intersection.edge.normalized());
            outputAngle = 20 * dot;
        } else {
            outputAngle = (inputAngle - 180) * 2;
        }

        RayMath.rotate(reflected, outputAngle);

        return reflected;
    }
}