package su.levenetc.androidplayground.raytracer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import su.levenetc.androidplayground.raytracer.drawers.DebugDrawer;
import su.levenetc.androidplayground.raytracer.drawers.Drawer;
import su.levenetc.androidplayground.raytracer.drawers.V1Drawer;
import su.levenetc.androidplayground.raytracer.lights.DirectedLight;
import su.levenetc.androidplayground.raytracer.lights.DirectedLightController;
import su.levenetc.androidplayground.raytracer.lights.Light;
import su.levenetc.androidplayground.raytracer.lights.LightController;
import su.levenetc.androidplayground.raytracer.lights.SingleRayLight;
import su.levenetc.androidplayground.raytracer.utils.Scenes;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayTracerView extends View {


    Scene scene;
    Drawer debugDrawer = new DebugDrawer();
    Drawer drawerV1 = new V1Drawer();
    LightController lightController;

    private boolean initRender;
    private Light light;
    private boolean debugScene = true;
    private boolean debugLight = true;

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

            //scene = Scenes.justVertical(width, height);
//            scene = Scenes.justVerticalTransparent(width, height);
            scene = Scenes.basicPrism(width, height);
//            scene = Scenes.basicLens(width, height);
            initLight(cx, cy);
        }
    }

    private void initLight(double cx, double cy) {
        light = new SingleRayLight(cx, cy, cx, cy + 450, Color.WHITE);
//        light = new ConeLight(cx, cy, cx + 300, cy + 300, Color.BLUE, 100);
        //light = new PlaneLight(cx, cy, cx + 100, cy, 80);
//        light = new PointLight(cx, cy, 300, 50);
//        lightController = new UndirectedLightController(light);
        lightController = new DirectedLightController((DirectedLight) light);
        RayTracer.trace(light, this.scene);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (lightController.onTouch(event)) {
            RayTracer.trace(light, scene);
            invalidate();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLACK);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        preRenderInit(width, height);

        if (debugScene) debugDrawer.draw(scene, canvas);
        if (debugLight) debugDrawer.draw(light, canvas);


        drawerV1.draw(light, canvas);
        lightController.draw(canvas);
    }

    public void setDebugScene(boolean debug) {
        this.debugScene = debug;
        invalidate();
    }

    public void setDebugLight(boolean debug) {
        this.debugLight = debug;
        invalidate();
    }
}
