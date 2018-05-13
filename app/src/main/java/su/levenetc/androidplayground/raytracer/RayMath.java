package su.levenetc.androidplayground.raytracer;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import su.levenetc.androidplayground.raytracer.geometry.Point;
import su.levenetc.androidplayground.raytracer.geometry.Segment;
import su.levenetc.androidplayground.raytracer.shapes.Shape;

import static java.lang.Double.compare;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayMath {

    private final static Intersection intersection = new Intersection();
    private final static double doubleEqualityEps = 0.0001;

    public static void rotate(Segment segment, double cx, double cy, double degrees) {
        double rads = Math.toRadians(degrees);
        double cos = Math.cos(rads);
        double sin = Math.sin(rads);

        //move to origin
        segment.x1 -= cx;
        segment.y1 -= cy;
        segment.x2 -= cx;
        segment.y2 -= cy;

        //rotate
        double x1 = segment.x1 * cos - segment.y1 * sin;
        double y1 = segment.x1 * sin + segment.y1 * cos;
        double x2 = segment.x2 * cos - segment.y2 * sin;
        double y2 = segment.x2 * sin + segment.y2 * cos;

        //move back
        segment.x1 = x1 + cx;
        segment.y1 = y1 + cy;
        segment.x2 = x2 + cx;
        segment.y2 = y2 + cy;
    }

    public static void rotate(Segment segment, double degrees) {
        double rads = Math.toRadians(degrees);
        double cos = Math.cos(rads);
        double sin = Math.sin(rads);

        //move to origin
        segment.x2 -= segment.x1;
        segment.y2 -= segment.y1;

        //rotate
        double x = segment.x2 * cos - segment.y2 * sin;
        double y = segment.x2 * sin + segment.y2 * cos;

        //move back
        segment.x2 = x + segment.x1;
        segment.y2 = y + segment.y1;
    }

    public static double dotProduct(Segment a, Segment b) {
        RaySegment aOrigin = toOrigin(a);
        RaySegment bOrigin = toOrigin(b);
        return aOrigin.x2 * bOrigin.x2 + aOrigin.y2 * bOrigin.y2;
    }

    public static double dotProduct(double x1, double y1, double x2, double y2,
                                    double x3, double y3, double x4, double y4) {
        //move to origin
        x2 = x2 - x1;
        y2 = y2 - y1;
        x1 = 0;
        y1 = 0;

        x4 = x4 - x3;
        y4 = y4 - y3;
        x3 = 0;
        y3 = 0;

        return x2 * x4 + y2 * y4;
    }

    static RaySegment toOrigin(Segment segment) {
        double diffX = segment.x1;
        double diffY = segment.y1;

        //TODO: cache instance
        return new RaySegment(0, 0, segment.x2 - diffX, segment.y2 - diffY);
    }

    public static double angleBetween(Segment a, Segment b) {
        return angleBetween(
                a.x1, a.y1, a.x2, a.y2,
                b.x1, b.y1, b.x2, b.y2);
    }

    public static double angleBetween(double ax1, double ay1, double ax2, double ay2,
                                      double bx1, double by1, double bx2, double by2) {
        double angleA = Math.atan2(ay1 - ay2, ax1 - ax2);
        double angleB = Math.atan2(by1 - by2, bx1 - bx2);
        double a = (angleB - angleA) * 180 / Math.PI;
        return a < 0 ? a * -1 : a;
    }

    public static boolean isReflectedByNormalAndIntersection(Segment ray, Edge edge) {
        return isReflectedByNormal(ray, edge) && hasIntersection(ray, edge);
    }

    public static boolean isReflectedByNormal(Segment ray, Edge edge) {

        boolean result = false;

        if (edge.hasNormal()) {

            if (edge instanceof DoubleSidedEdge) {
                DoubleSidedEdge dEdge = (DoubleSidedEdge) edge;
                result = dotProduct(ray, dEdge.leftNormal) < 0 || dotProduct(ray, dEdge.rightNormal) < 0;
            } else {
                result = dotProduct(ray, edge.normal) < 0;
            }

        }

        return result;
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }

    public static Intersection getClosestIntersection(RaySegment ray, Scene scene) {

        //TODO: use cached list
        List<Edge> boundaries = new LinkedList<>();

        intersection.bound = null;
        intersection.point = null;

        //get all intersection edges with array
        for (Shape object : scene.objects)
            for (Edge bound : object.edges)
                if (isReflectedByNormalAndIntersection(ray, bound))
                    boundaries.add(bound);

        if (boundaries.isEmpty()) {
            return null;
        } else if (boundaries.size() == 1) {
            intersection.bound = boundaries.get(0);
            intersection.point = getIntersection(ray, boundaries.get(0));
            return intersection;
        }

        double minDist = Double.MAX_VALUE;

        //get all intersection points and take closest
        for (Edge bound : boundaries) {
            Point inter = getIntersection(ray, bound);
            double dist = distance(inter.x, inter.y, ray.x1, ray.y1);

            if (compare(dist, 0) > 0 && compare(dist, minDist) < 0) {
                minDist = dist;
                intersection.point = inter;
                intersection.bound = bound;
            }
        }

        return intersection;
    }


    public static Point getIntersection(RaySegment a, Edge b) {
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
                                        RaySegment b) {
        return getIntersection(x1, y1, x2, y2, b.x1, b.y1, b.x2, b.y2);
    }

    public static boolean hasIntersection(Segment a, Segment b) {

        double x1 = a.x1;
        double y1 = a.y1;
        double x2 = a.x2;
        double y2 = a.y2;

        double x3 = b.x1;
        double y3 = b.y1;
        double x4 = b.x2;
        double y4 = b.y2;

        boolean has = hasIntersection(x1, y1, x2, y2, x3, y3, x4, y4);

        if (has) {
            Point intersection = getIntersection(x1, y1, x2, y2, x3, y3, x4, y4);
            if (pointAtEnds(intersection.x, intersection.y, x1, y1, x2, y2) || pointAtEnds(intersection.x, intersection.y, x3, y3, x4, y4)) {
                has = false;
            }
        }

        return has;
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

    static class Intersection {
        public Point point;
        public Edge bound;
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
        return compare(ccw, 0.0);
    }

    private static boolean pointAtEnds(double px, double py, double x1, double y1, double x2, double y2) {
        return (doubleEquals(px, x1) && doubleEquals(py, y1)) || (doubleEquals(px, x2) && doubleEquals(py, y2));
    }

    private static boolean doubleEquals(double a, double b) {
        double diff = Math.abs(a - b);
        return diff <= doubleEqualityEps;
    }
}
