package su.levenetc.androidplayground;

import org.assertj.core.data.Offset;
import org.junit.Test;

import su.levenetc.androidplayground.raytracer.Line;
import su.levenetc.androidplayground.raytracer.RayMath;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eugene.levenetc on 26/03/2018.
 */

public class RayMathTests {

    @Test
    public void testAngles() {
        assertThat(RayMath.angleBetween(horiz(), se45())).isEqualTo(-315);
        assertThat(RayMath.angleBetween(horiz(), vertDown())).isEqualTo(-270);
        assertThat(RayMath.angleBetween(horiz(), horizOpposite())).isEqualTo(-180);
    }

    @Test
    public void testMagnitudes() {
        assertThat(horiz().length()).isEqualTo(100);
        assertThat(horizOpposite().length()).isEqualTo(100);
        assertThat(se45().length()).isCloseTo(141, Offset.offset(0.5));
    }

    @Test
    public void normals() {
        Line horiz = horiz();

        horiz.initRightNormal();

        assertThat(RayMath.angleBetween(horiz.normal(), sw45())).isEqualTo(45);
        assertThat(RayMath.angleBetween(horiz.normal(), se45())).isEqualTo(-45);


    }

    @Test
    public void testReflectedByNormal() {
        Line ground = horiz();
        ground.initRightNormal();

        assertThat(RayMath.isReflectedByNormal(sw45(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormal(se45(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormal(vertDown(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormal(vertUp(), ground)).isFalse();
    }

    @Test
    public void testReflectedByIntersectionAndNormal() {
        Line ground = horiz();
        ground.initRightNormal();

        assertThat(RayMath.isReflectedByNormalAndIntersection(sw45(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormalAndIntersection(se45(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormalAndIntersection(vertDown(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormalAndIntersection(vertUp(), ground)).isFalse();
    }

    @Test
    public void testDirections() {
        assertThat(sw45().direction()).isEqualTo(Line.Direction.SW);
        assertThat(se45().direction()).isEqualTo(Line.Direction.SE);
    }

    @Test
    public void testDotProducts() {

        Line vert = vertDown();
        vert.initLeftNormal();

        assertThat(RayMath.dotProduct(se45(), vert)).isGreaterThan(0);
        assertThat(RayMath.dotProduct(sw45(), vert)).isLessThan(0);
    }


    static Line vertUp() {
        return new Line(0, 0, 0, -100);
    }

    static Line vertDown() {
        return new Line(0, 0, 0, 100);
    }

    static Line horiz() {
        return new Line(0, 0, 100, 0);
    }

    static Line horizOpposite() {
        return new Line(0, 0, -100, 0);
    }

    static Line se45() {
        return new Line(0, 0, 100, 100);
    }

    static Line sw45() {
        return new Line(100, 0, 0, 100);
    }

}
