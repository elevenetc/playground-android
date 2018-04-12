package su.levenetc.androidplayground;

import org.assertj.core.data.Offset;
import org.junit.Test;

import su.levenetc.androidplayground.raytracer.RayMath;
import su.levenetc.androidplayground.raytracer.RaySegment;
import su.levenetc.androidplayground.raytracer.geometry.Point;
import su.levenetc.androidplayground.utils.Out;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eugene.levenetc on 26/03/2018.
 */

public class RayMathTests {

    @Test
    public void testAngles() {
        assertThat(RayMath.angleBetween(we(), se45())).isEqualTo(-315);
        assertThat(RayMath.angleBetween(we(), vertDown())).isEqualTo(-270);
        assertThat(RayMath.angleBetween(we(), horizOpposite())).isEqualTo(-180);
    }

    @Test
    public void testMagnitudes() {
        assertThat(we().length()).isEqualTo(100);
        assertThat(horizOpposite().length()).isEqualTo(100);
        assertThat(se45().length()).isCloseTo(141, Offset.offset(0.5));
    }

    static RaySegment vertUp() {
        return new RaySegment(0, 0, 0, -100);
    }

    static RaySegment vertDown() {
        return new RaySegment(0, 0, 0, 100);
    }

    static RaySegment we() {
        return new RaySegment(0, 0, 100, 0);
    }

    static RaySegment horizOpposite() {
        return new RaySegment(0, 0, -100, 0);
    }

    static RaySegment se45() {
        return new RaySegment(0, 0, 100, 100);
    }

    static RaySegment sw45() {
        return new RaySegment(100, 0, 0, 100);
    }

    @Test
    public void normals() {
        RaySegment horiz = we();

        horiz.initRightNormal();

        assertThat(RayMath.angleBetween(horiz.normal(), sw45())).isEqualTo(45);
        assertThat(RayMath.angleBetween(horiz.normal(), se45())).isEqualTo(-45);
    }

    @Test
    public void testReflectedByNormal() {
        RaySegment ground = we();
        ground.initRightNormal();

        assertThat(RayMath.isReflectedByNormal(sw45(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormal(se45(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormal(vertDown(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormal(vertUp(), ground)).isFalse();
    }

    @Test
    public void testReflectedByIntersectionAndNormal() {
        RaySegment ground = we();
        ground.initRightNormal();

        assertThat(RayMath.isReflectedByNormalAndIntersection(sw45(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormalAndIntersection(se45(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormalAndIntersection(vertDown(), ground)).isTrue();
        assertThat(RayMath.isReflectedByNormalAndIntersection(vertUp(), ground)).isFalse();
    }

    @Test
    public void testDirections() {
        assertThat(sw45().direction()).isEqualTo(RaySegment.Direction.SW);
        assertThat(se45().direction()).isEqualTo(RaySegment.Direction.SE);
    }

    @Test
    public void testDotProducts() {

        RaySegment vert = vertDown();
        vert.initLeftNormal();

        assertThat(RayMath.dotProduct(se45(), vert)).isGreaterThan(0);
        assertThat(RayMath.dotProduct(sw45(), vert)).isLessThan(0);
    }

    @Test
    public void testReflection() {
        RaySegment raySegment = we();
        //RaySegment ray = sw45();
        //ray.translate(0, -50);
        RaySegment ray = new RaySegment(100, -25, 0, 50);


        raySegment.initLeftNormal();
        Out.pln(RayMath.isReflectedByNormalAndIntersection(ray, raySegment));
        double angle = RayMath.angleBetween(ray, raySegment);
        Out.pln("angle", angle);
        Point intersection = RayMath.getIntersection(ray, raySegment);
        Out.pln(intersection);

        RaySegment nextRay = ray.copy();
        nextRay.translateTo(intersection);
        Out.pln();
        Out.pln("original", ray);
        Out.pln("translated", nextRay);
        Out.pln();

        double newAngle = (angle - 180) * 2;

        RayMath.rotateSegment(nextRay, newAngle);

        Out.pln("rotated", nextRay);
        Out.pln();
    }

}
