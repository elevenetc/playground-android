package su.levenetc.androidplayground.raytracer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayCasterView extends View {


    Scene scene = new Scene();

    private Triangle triangle = new Triangle();
    private Path path;
    private Rect boundRect;
    private boolean initRender;
    private Light light;

    public RayCasterView(Context context) {
        super(context);


    }

    public RayCasterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {

    }

    private void preRenderInit(int width, int height) {
        if (!initRender) {
            initRender = true;

            double cx = width / 2;
            double cy = height / 2;


            path = new Path.Builder()
                    .add(cx, cy - 75)
                    .append(100, 100)
                    .append(100, 0)
                    .append(100, -50)
                    .append(100, 150)
                    .append(0, 150)
                    .append(-100, 0)
                    .append(-100, -100)
                    .append(-100, 0)
                    .append(0, -50)
                    .build();

            path.initRightNormals();
            scene.add(path);

            light = new ConeLight(cx - 300, cy, cx + 2000, cy + 800);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        boundRect = new Rect(top, left, right, bottom);
        //boundRect.initRightNormals();
        scene.add(boundRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLACK);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        preRenderInit(width, height);

        RayDrawer.draw(boundRect, canvas);

        for (Ray ray : light.rays) {
            RayTracer.trace(ray, scene);
            RayDrawer.draw(ray, canvas);
        }

        RayDrawer.draw(path, canvas);
    }
}
