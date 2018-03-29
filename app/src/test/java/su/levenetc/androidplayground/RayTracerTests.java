package su.levenetc.androidplayground;

import org.assertj.core.data.Offset;
import org.junit.Test;

import su.levenetc.androidplayground.raytracer.Line;
import su.levenetc.androidplayground.raytracer.RayMath;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eugene.levenetc on 26/03/2018.
 */

public class RayTracerTests {
    @Test
    public void perpendicularDotProduct() {
        Line horiz = new Line(0, 0, 100, 0);
        Line vert = new Line(0, 0, 0, 100);

        assertThat(RayMath.dotProduct(horiz, vert)).isZero();
    }

    @Test
    public void testAngles() {
        assertThat(RayMath.angleBetween(horiz(), diag45())).isEqualTo(-315);
        assertThat(RayMath.angleBetween(horiz(), vert())).isEqualTo(-270);
        assertThat(RayMath.angleBetween(horiz(), horizOpposite())).isEqualTo(-180);
    }

    @Test
    public void testMagnitudes() {
        assertThat(horiz().length()).isEqualTo(100);
        assertThat(horizOpposite().length()).isEqualTo(100);
        assertThat(diag45().length()).isCloseTo(141, Offset.offset(0.5));
    }

    @Test
    public void normals() {
        Line diag = diag45();
        diag.translate(50, 50);
        diag.initLeftNormal();
    }

    @Test
    public void test() {
        Line a = new Line(10, 10, 200, 10);
        Line b = new Line(10, 20, 20, 10);
        double angle = RayMath.angleBetween(a, b);
    }

    static Line vert() {
        return new Line(0, 0, 0, 100);
    }

    static Line horiz() {
        return new Line(0, 0, 100, 0);
    }

    static Line horizOpposite() {
        return new Line(0, 0, -100, 0);
    }

    static Line diag45() {
        return new Line(0, 0, 100, 100);
    }

}
