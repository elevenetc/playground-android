package su.levenetc.androidplayground.raycaster;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayCaster {
    public static void cast(Ray ray, EnvModel model) {

        Vector wall = RayMath.getClosestWall(ray.initVector, model);
        Point intersection = RayMath.getIntersection(ray.initVector, wall);

        ray.vectors.add(new Vector(ray.initVector.x1, ray.initVector.y1, intersection.x, intersection.y));

    }
}