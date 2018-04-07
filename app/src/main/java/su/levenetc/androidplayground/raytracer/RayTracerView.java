package su.levenetc.androidplayground.raytracer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import su.levenetc.androidplayground.raytracer.drawers.DebugDrawer;
import su.levenetc.androidplayground.raytracer.drawers.Drawer;
import su.levenetc.androidplayground.raytracer.drawers.V1Drawer;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayTracerView extends View {


    Scene scene = new Scene();
    Drawer debugDrawer = new DebugDrawer();
    Drawer drawerV1 = new V1Drawer();

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

//            Path path = new Path.Builder()
//                    .add(initX, initY)
//                    .append(100, 100)
//                    .append(100, 0)
//                    .append(100, -50)
//                    .append(100, 150)
//                    .append(0, 150)
//                    .append(-100, 0)
//                    .append(-100, -100)
//                    .append(-100, 0)
//                    .append(0, -50)
//                    .append(-50, 0)
//                    .append(0, 100)
//                    .append(100, 0)
//                    .append(100, 100)
//                    .append(200, 0)
//                    .append(0, -300)
//                    .append(-100, -100)
//                    .append(-100, 0)
//                    .append(-100, 50)
//                    .add(initX, initY)
//                    .build();
//
//            path.initRightNormals();
//            scene.add(path);

            //

            Rect boundRect = new Rect(60, 60, width - 60, height - 60);
            boundRect.initRightNormals();
            scene.add(boundRect);

            //

            Rect dummyRect01 = Rect.byLoc(700, 300, 150, 150);
            dummyRect01.initLeftNormals();
            scene.add(dummyRect01);

            Rect dummyRect02 = Rect.byLoc(300, 470, 150, 150);
            dummyRect02.initLeftNormals();
            scene.add(dummyRect02);

            Rect dummyRect03 = Rect.byLoc(300, 670, 250, 20);
            dummyRect03.initLeftNormals();
            scene.add(dummyRect03);

            Rect dummyRect04 = Rect.byLoc(400, 770, 50, 50);
            dummyRect04.initLeftNormals();
            scene.add(dummyRect04);

            Rect dummyRect05 = Rect.byLoc(400, 850, 50, 50);
            dummyRect05.initLeftNormals();
            scene.add(dummyRect05);

            Rect dummyRect06 = Rect.byLoc(400, 930, 50, 50);
            dummyRect06.initLeftNormals();
            scene.add(dummyRect06);

            Rect dummyRect10 = Rect.byLoc(600, 1200, 550, 20);
            dummyRect10.initLeftNormals();
            scene.add(dummyRect10);

            //

            light = new ConeLight(
                    300,
                    cx - 300, cy,
                    cx + 2000, cy + 800
            );
            RayTracer.trace(light, scene);
        }
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

        //debugDrawer.draw(scene, canvas);
        //debugDrawer.draw(light, canvas);
        long start = System.currentTimeMillis();
        drawerV1.draw(light, canvas);
        Log.d("time-to-draw", String.valueOf(System.currentTimeMillis() - start));
    }
}
