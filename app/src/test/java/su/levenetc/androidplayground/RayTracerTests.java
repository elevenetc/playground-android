package su.levenetc.androidplayground;

import org.assertj.core.data.Offset;
import org.junit.Test;

import su.levenetc.androidplayground.raytracer.RayMath;
import su.levenetc.androidplayground.raytracer.Vector;
import su.levenetc.androidplayground.utils.Out;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eugene.levenetc on 26/03/2018.
 */

public class RayTracerTests {
    @Test
    public void perpendicularDotProduct() {
        Vector horiz = new Vector(0, 0, 100, 0);
        Vector vert = new Vector(0, 0, 0, 100);

        assertThat(RayMath.dotProduct(horiz, vert)).isZero();
    }

    @Test
    public void test01() {
        Vector horiz = new Vector(0, 0, 100, 0);
        Vector vert = new Vector(0, 0, 100, 100);

        double actual = RayMath.dotProduct(horiz, vert);
        assertThat(actual).isZero();
    }

    @Test
    public void testAngles() {
        assertThat(RayMath.angleBetween(horiz(), diag45())).isEqualTo(45);
        assertThat(RayMath.angleBetween(horiz(), vert())).isEqualTo(90);
        assertThat(RayMath.angleBetween(horiz(), horizOpposite())).isEqualTo(180);
    }

    @Test
    public void testMagnitudes() {
        assertThat(horiz().magnitude()).isEqualTo(100);
        assertThat(horizOpposite().magnitude()).isEqualTo(100);
        assertThat(diag45().magnitude()).isCloseTo(141, Offset.offset(0.5));
    }

    @Test
    public void test() {
        Vector a = new Vector(10, 10, 200, 10);
        Vector b = new Vector(10, 20, 20, 10);
        double angle = RayMath.angleBetween(a, b);
        Out.pln(angle);
    }

    static Vector vert() {
        return new Vector(0, 0, 0, 100);
    }

    static Vector horiz() {
        return new Vector(0, 0, 100, 0);
    }

    static Vector horizOpposite() {
        return new Vector(0, 0, -100, 0);
    }

    static Vector diag45() {
        return new Vector(0, 0, 100, 100);
    }

}
