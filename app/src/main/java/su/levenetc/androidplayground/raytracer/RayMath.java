package su.levenetc.androidplayground.raytracer;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayMath {

    public static void rotateLine(Line line, double degrees) {
        double rads = Math.toRadians(degrees);
        double cos = Math.cos(rads);
        double sin = Math.sin(rads);

        //move to origin
        line.x2 -= line.x1;
        line.y2 -= line.y1;

        double x = line.x2 * cos - line.y2 * sin;
        double y = line.x2 * sin + line.y2 * cos;

        //move back
        line.x2 = x + line.x1;
        line.y2 = y + line.y1;
    }

    public static double dotProduct(Line ray, Line line) {
        Line rayOrigin = toOrigin(ray);
        Line normalOrigin = toOrigin(line.normal);
        return rayOrigin.x2 * normalOrigin.x2 + rayOrigin.y2 * normalOrigin.y2;
    }

    static Line toOrigin(Line line) {
        double diffX = line.x1;
        double diffY = line.y1;

        //TODO: cache instance
        return new Line(0, 0, line.x2 - diffX, line.y2 - diffY);
    }

    public static double angleBetween(Line a, Line b) {
        return angleBetween(
                a.x1, a.y1, a.x2, a.y2,
                b.x1, b.y1, b.x2, b.y2);
    }

    public static double angleBetween(double ax1, double ay1, double ax2, double ay2,
                                      double bx1, double by1, double bx2, double by2) {
        double angleA = Math.atan2(ay1 - ay2, ax1 - ax2);
        double angleB = Math.atan2(by1 - by2, bx1 - bx2);
        return (angleB - angleA) * 180 / Math.PI;
    }

    public static boolean isReflectedByNormalAndIntersection(Line ray, Line line) {
        boolean hasIntersection = hasIntersection(ray, line);
        boolean reflectedByNormal = isReflectedByNormal(ray, line);
        return hasIntersection && reflectedByNormal;
    }

    public static boolean isReflectedByNormal(Line ray, Line line) {

        boolean result = false;

        if (line.hasNormal()) {
            result = dotProduct(ray, line) < 0;
        }

        return result;
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }

    public static Point getClosestWallIntersection(Line ray, Scene scene) {

        //TODO: use cached list
        List<Line> boundaries = new LinkedList<>();

        //get all intersection lines with array
        for (Shape object : scene.objects)
            for (Line bound : object.lines)
                if (isReflectedByNormalAndIntersection(ray, bound))
                    boundaries.add(bound);

        if (boundaries.isEmpty()) {
            return null;
        } else if (boundaries.size() == 1) {
            return getIntersection(ray, boundaries.get(0));
        }

        double minDist = Double.MAX_VALUE;
        Point result = null;

        //get all intersection points and take closest
        for (Line bound : boundaries) {
            Point inter = getIntersection(ray, bound);
            double dist = distance(inter.x, inter.y, ray.x1, ray.y1);

            if (dist < minDist) {
                minDist = dist;
                result = inter;
            }
        }

        return result;
    }

    public static Point getIntersection(Line a, Line b) {
        double x1 = a.x1;
        double y1 = a.y1;
        double x2 = a.x2;
        double y2 = a.y2;

        double x3 = b.x1;
        double y3 = b.y1;
        double x4 = b.x2;
        double y4 = b.y2;

        return getIntersection(x1, y1, x2, y2, x3, y3, x4, y4);
    }

    public static Point getIntersection(double x1, double y1, double x2, double y2,
                                        Line b) {
        return getIntersection(x1, y1, x2, y2, b.x1, b.y1, b.x2, b.y2);
    }

    @NonNull
    public static Point getIntersection(
            double x1, double y1, double x2, double y2,
            double x3, double y3, double x4, double y4) {
        double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        double x = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
        double y = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

        return new Point(x, y);
    }

    public static boolean hasIntersection(Line a, Line b) {

        double x1 = a.x1;
        double y1 = a.y1;
        double x2 = a.x2;
        double y2 = a.y2;

        double x3 = b.x1;
        double y3 = b.y1;
        double x4 = b.x2;
        double y4 = b.y2;

        return hasIntersection(x1, y1, x2, y2, x3, y3, x4, y4);
    }

    public static boolean hasIntersection(
            double x1, double y1, double x2, double y2,
            double x3, double y3, double x4, double y4
    ) {
        return ((relativeCCW(x1, y1, x2, y2, x3, y3) *
                relativeCCW(x1, y1, x2, y2, x4, y4) <= 0)
                && (relativeCCW(x3, y3, x4, y4, x1, y1) *
                relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
    }

    /**
     * From java.awt.geom.Java2D
     */
    private static int relativeCCW(double x1, double y1,
                                   double x2, double y2,
                                   double px, double py) {
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        double ccw = px * y2 - py * x2;
        if (ccw == 0.0) {
            ccw = px * x2 + py * y2;
            if (ccw > 0.0) {
                px -= x2;
                py -= y2;
                ccw = px * x2 + py * y2;
                if (ccw < 0.0) {
                    ccw = 0.0;
                }
            }
        }
        return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    }
}
