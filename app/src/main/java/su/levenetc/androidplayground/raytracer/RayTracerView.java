package su.levenetc.androidplayground.raytracer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayTracerView extends View {


    Scene scene = new Scene();

    private boolean initRender;
    private Light light;

    public RayTracerView(Context context) {
        super(context);
        init();
    }

    public RayTracerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    private void preRenderInit(int width, int height) {
        if (!initRender) {
            initRender = true;

            double cx = width / 2;
            double cy = height / 2;

            double initX = cx;
            double initY = cy - 75;

            Path path = new Path.Builder()
                    .add(initX, initY)
                    .append(100, 100)
                    .append(100, 0)
                    .append(100, -50)
                    .append(100, 150)
                    .append(0, 150)
                    .append(-100, 0)
                    .append(-100, -100)
                    .append(-100, 0)
                    .append(0, -50)
                    .append(-50, 0)
                    .append(0, 100)
                    .append(100, 0)
                    .append(100, 100)
                    .append(200, 0)
                    .append(0, -300)
                    .append(-100, -100)
                    .append(-100, 0)
                    .append(-100, 50)
                    .add(initX, initY)
                    .build();

            path.initRightNormals();
            scene.add(path);

            //

            Rect boundRect = new Rect(60, 60, width - 60, height - 60);
            boundRect.initRightNormals();
            scene.add(boundRect);

            //

            Rect dummyRect = new Rect(600, 200, 300, 800);
            dummyRect.initLeftNormals();
            scene.add(dummyRect);

            //

            light = new ConeLight(cx - 300, cy, cx + 2000, cy + 800);
            RayTracer.trace(light, scene);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {

        } else if (action == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            light.updatePosition(x, y);
            RayTracer.trace(light, scene);
            invalidate();
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {

        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLACK);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        preRenderInit(width, height);

        RayDrawer.draw(scene, canvas);
        RayDrawer.draw(light, canvas);
    }
}
