package su.levenetc.androidplayground;

import org.junit.Test;

import su.levenetc.androidplayground.utils.Point;
import su.levenetc.androidplayground.utils.Vector;

import static org.junit.Assert.assertEquals;

/**
 * Created by eugene.levenetc on 13/07/2017.
 */
public class RaySegmentTests {
    @Test
    public void testIntersection01() {
        Vector vector = new Vector(null, null);
        assertEquals(new Point(0, 0), vector.computeIntersect(
                new Point(-1, -1),
                new Point(1, 1),
                new Point(-1, 1),
                new Point(1, -1)
        ));
    }

    @Test
    public void testIntersection02() {
        Vector vector = new Vector(null, null);
        assertEquals(new Point(1, 1), vector.computeIntersect(
                new Point(0, 0),
                new Point(2, 2),
                new Point(0, 1),
                new Point(2, 1)
        ));
    }

}
