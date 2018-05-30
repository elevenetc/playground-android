package su.levenetc.androidplayground.raytracer;

public class EdgeFactories {

    public static EdgeFactory transparent() {
        return TransparentEdge::new;
    }

    public static EdgeFactory basic() {
        return Edge::new;
    }

    public interface EdgeFactory {
        Edge create();
    }
}
