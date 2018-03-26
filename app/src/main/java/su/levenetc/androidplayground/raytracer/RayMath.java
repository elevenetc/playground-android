package su.levenetc.androidplayground.raytracer;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayMath {

    public static double angleBetween(Vector a, Vector b) {
        double angle1 = Math.atan2(a.y1 - a.y2, a.x1 - a.x2);
        double angle2 = Math.atan2(b.y1 - b.y2, b.x1 - b.x2);
        double result = (angle2 - angle1) * 180 / Math.PI;
        if (result < 0) {
            result += 360;
        }
        return result;
    }

    public static double dotProduct(Vector a, Vector b) {
        return a.x1 * b.x1 + a.y1 * b.y1 +
                a.x2 * b.x2 + a.y2 * b.y2;
    }

    public static double angle(Vector v1, Vector v2) {
        //double normProduct = v1.getNorm() * v2.getNorm();
//        if (normProduct == 0) {
//            throw new MathArithmeticException(LocalizedFormats.ZERO_NORM);
//        }
//
//        double dot = v1.dotProduct(v2);
//        double threshold = normProduct * 0.9999;
//        if ((dot < -threshold) || (dot > threshold)) {
//            // the vectors are almost aligned, compute using the sine
//            final double n = FastMath.abs(MathArrays.linearCombination(v1.x, v2.y, -v1.y, v2.x));
//            if (dot >= 0) {
//                return FastMath.asin(n / normProduct);
//            }
//            return FastMath.PI - FastMath.asin(n / normProduct);
//        }
//
//        // the vectors are sufficiently separated to use the cosine
//        return FastMath.acos(dot / normProduct);

        return 0;
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }

    public static Point getClosestWallIntersection(Vector vector, Rect model) {
        List<Vector> walls = new LinkedList<>();

        //get all bounds towards ray
        for (Vector bound : model.allBounds) {
            if (hasIntersection(vector, bound)) {
                walls.add(bound);
            }
        }

        if (walls.isEmpty()) {
            return null;
        } else if (walls.size() == 1) {
            return getIntersection(vector, walls.get(0));
        }

        List<Point> intersections = new LinkedList<>();
        double minDist = Double.MAX_VALUE;
        Point result = null;

        //get all intersection points and take with min dist
        for (Vector wall : walls) {
            Point inter = getIntersection(vector, wall);
            double dist = distance(inter.x, inter.y, vector.x1, vector.y1);

            if (dist < minDist) {
                minDist = dist;
                result = inter;
            }
        }

        return result;
    }

    public static Point getIntersection(Vector a, Vector b) {
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
                                        Vector b) {
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

    public static boolean hasIntersection(Vector a, Vector b) {

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
