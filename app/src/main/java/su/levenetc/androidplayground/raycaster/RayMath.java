package su.levenetc.androidplayground.raycaster;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayMath {

    public static double angle(Vector v1, Vector v2) {
        double normProduct = v1.getNorm() * v2.getNorm();
        if (normProduct == 0) {
            throw new MathArithmeticException(LocalizedFormats.ZERO_NORM);
        }

        double dot = v1.dotProduct(v2);
        double threshold = normProduct * 0.9999;
        if ((dot < -threshold) || (dot > threshold)) {
            // the vectors are almost aligned, compute using the sine
            final double n = FastMath.abs(MathArrays.linearCombination(v1.x, v2.y, -v1.y, v2.x));
            if (dot >= 0) {
                return FastMath.asin(n / normProduct);
            }
            return FastMath.PI - FastMath.asin(n / normProduct);
        }

        // the vectors are sufficiently separated to use the cosine
        return FastMath.acos(dot / normProduct);
    }

    public static Point getClosestWall(Vector vector, EnvModel model) {
        List<Vector> walls = new LinkedList<>();
        for (Vector bound : model.allBounds) {
            if (hasIntersection(vector, bound)) {
                walls.add(bound);
            }
        }

        if (walls.isEmpty()) return null;

        List<Point> intersections = new LinkedList<>();

        for (Vector wall : walls) {
            Point intersection = getIntersection(vector, wall);
            intersections.add(intersection);
        }

        return null;
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
