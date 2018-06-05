package su.levenetc.androidplayground;

import org.assertj.core.data.Offset;
import org.junit.Test;

import su.levenetc.androidplayground.raytracer.RaySegment;
import su.levenetc.androidplayground.raytracer.geometry.Point;
import su.levenetc.androidplayground.raytracer.math.RayMathV1;
import su.levenetc.androidplayground.utils.Out;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eugene.levenetc on 26/03/2018.
 */

public class RayMathTests {

    @Test
    public void testAngles() {
        assertThat(RayMathV1.angleBetween(we(), se45())).isEqualTo(-315);
        assertThat(RayMathV1.angleBetween(we(), vertDown())).isEqualTo(-270);
        assertThat(RayMathV1.angleBetween(we(), horizOpposite())).isEqualTo(-180);
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

    @Test
    public void normals() {
        SingleSidedEdge horiz = we();

        horiz.setRightNormal();

        assertThat(RayMathV1.angleBetween(horiz.normal(), sw45())).isEqualTo(45);
        assertThat(RayMathV1.angleBetween(horiz.normal(), se45())).isEqualTo(-45);
    }

    @Test
    public void testReflectedByNormal() {
        SingleSidedEdge ground = we();
        ground.setRightNormal();

        assertThat(RayMathV1.getIntersectionByNormal(sw45(), ground)).isTrue();
        assertThat(RayMathV1.getIntersectionByNormal(se45(), ground)).isTrue();
        assertThat(RayMathV1.getIntersectionByNormal(vertDown(), ground)).isTrue();
        assertThat(RayMathV1.getIntersectionByNormal(vertUp(), ground)).isFalse();
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
    public void testReflectedByIntersectionAndNormal() {
        SingleSidedEdge ground = we();
        ground.setRightNormal();

        assertThat(RayMathV1.isReflectedByNormalAndIntersection(sw45(), ground)).isTrue();
        assertThat(RayMathV1.isReflectedByNormalAndIntersection(se45(), ground)).isTrue();
        assertThat(RayMathV1.isReflectedByNormalAndIntersection(vertDown(), ground)).isTrue();
        assertThat(RayMathV1.isReflectedByNormalAndIntersection(vertUp(), ground)).isFalse();
    }

    @Test
    public void testDotProducts() {

        SingleSidedEdge vert = vertDown();
        vert.setLeftNormal();

        assertThat(RayMathV1.dotProduct(se45(), vert)).isGreaterThan(0);
        assertThat(RayMathV1.dotProduct(sw45(), vert)).isLessThan(0);
    }

    @Test
    public void testReflection() {
        SingleSidedEdge raySegment = we();
        //RaySegment ray = sw45();
        //ray.translate(0, -50);
        RaySegment ray = new RaySegment(100, -25, 0, 50);


        raySegment.setLeftNormal();
        Out.pln(RayMathV1.isReflectedByNormalAndIntersection(ray, raySegment));
        double angle = RayMathV1.angleBetween(ray, raySegment);
        Out.pln("angle", angle);
        Point intersection = RayMathV1.getIntersection(ray, raySegment);
        Out.pln(intersection);

        RaySegment nextRay = ray.copy();
        nextRay.translateTo(intersection);
        Out.pln();
        Out.pln("original", ray);
        Out.pln("translated", nextRay);
        Out.pln();

        double newAngle = (angle - 180) * 2;

        RayMathV1.rotate(nextRay, newAngle);

        Out.pln("rotated", nextRay);
        Out.pln();
    }

    @Test
    public void testDirections() {
        assertThat(sw45().direction()).isEqualTo(RaySegment.Direction.SW);
        assertThat(se45().direction()).isEqualTo(RaySegment.Direction.SE);
    }

    static SingleSidedEdge vertDown() {
        return new SingleSidedEdge(0, 0, 0, 100);
    }

    static SingleSidedEdge we() {
        return new SingleSidedEdge(0, 0, 100, 0);
    }

}
