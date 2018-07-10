package su.levenetc.androidplayground.raytracer.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.drawers.Drawer;
import su.levenetc.androidplayground.raytracer.drawers.LinearDrawer;
import su.levenetc.androidplayground.raytracer.lights.ConeLight;
import su.levenetc.androidplayground.raytracer.lights.DirectedLight;
import su.levenetc.androidplayground.raytracer.lights.DirectedLightController;
import su.levenetc.androidplayground.raytracer.lights.Light;
import su.levenetc.androidplayground.raytracer.lights.LightController;
import su.levenetc.androidplayground.raytracer.math.RayMath;
import su.levenetc.androidplayground.raytracer.math.RayMathV1;
import su.levenetc.androidplayground.raytracer.renderers.RenderThread;
import su.levenetc.androidplayground.raytracer.tracers.RayTracer;
import su.levenetc.androidplayground.raytracer.tracers.RayTracerV1;
import su.levenetc.androidplayground.raytracer.utils.Scenes;

public class BackgroundRayTracerView extends View implements RayTraceView {

    private final float spotSize = 1.0f;
    //private Drawer drawer = new SpotDrawer(spotSize, spotSize);
    private Drawer drawer = new LinearDrawer(spotSize);
    private RayMath math = new RayMathV1();
    private RayTracer tracer = new RayTracerV1(math);
    private Canvas canvas;
    private Scene scene;
    private Light light;
    private LightController lightController;
    private boolean initRender;
    private Bitmap bitmap;
    private Paint paint = new Paint();
    private RenderThread renderThread;

    public BackgroundRayTracerView(Context context) {
        super(context);
        init();
    }

    @Override
    public void setDebugLight(boolean value) {

    }

    @Override
    public void setDebugScene(boolean value) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        boolean result = lightController.onTouch(event);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            renderThread.draw();
        } else {
            if (result) invalidate();
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        preRenderInit(canvas.getWidth(), canvas.getHeight());

        if (renderThread.isDrawing()) {
            canvas.drawColor(Color.BLACK);
        } else {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }

        lightController.draw(canvas);
    }

    private void init() {

    }

    private void preRenderInit(int width, int height) {
        if (!initRender) {
            initRender = true;
            double cx = width / 2;
            double cy = height / 2;
            scene = Scenes.basicLens(width, height);
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            initLight(cx, cy);

            renderThread = new RenderThread(tracer, scene, drawer, light, canvas, () -> post(this::invalidate));
            renderThread.start();
        }
    }

    private void initLight(double cx, double cy) {
//        light = new SingleRayLight(cx, cy, cx + 500, cy, Color.WHITE);
//        light = new PlaneLight(cx, cy, cx + 800, cy, spotSize, Color.WHITE, 6000);
        light = new ConeLight(cx, cy, cx + 800, cy, Color.WHITE, 1000, math);
        light.setBrightness(.07f);
        lightController = new DirectedLightController((DirectedLight) light);
    }

}