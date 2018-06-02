package su.levenetc.androidplayground.raytracer.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import su.levenetc.androidplayground.raytracer.RayTracer;
import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.drawers.Drawer;
import su.levenetc.androidplayground.raytracer.drawers.V1Drawer;
import su.levenetc.androidplayground.raytracer.lights.DirectedLight;
import su.levenetc.androidplayground.raytracer.lights.DirectedLightController;
import su.levenetc.androidplayground.raytracer.lights.Light;
import su.levenetc.androidplayground.raytracer.lights.LightController;
import su.levenetc.androidplayground.raytracer.lights.PlaneLight;
import su.levenetc.androidplayground.raytracer.utils.Scenes;

public class BackgroundRayTracerView extends View implements RayTraceView {

    private Drawer drawer = new V1Drawer();
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

        if (lightController.onTouch(event)) {
            renderThread.draw();
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

        this.canvas.drawColor(Color.BLACK);

        //drawer.draw(light, this.canvas);
        lightController.draw(this.canvas);
        canvas.drawBitmap(bitmap, 0, 0, paint);
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

            renderThread = new RenderThread(scene, drawer, light, canvas, () -> post(this::invalidate));
        }
    }

    private void initLight(double cx, double cy) {
        light = new PlaneLight(cx, cy, cx + 500, cy, Color.WHITE, 80);
        light.setBrightness(0.05f);
        lightController = new DirectedLightController((DirectedLight) light);
        RayTracer.trace(light, this.scene);
    }

    static class RenderThread extends Thread {

        private Scene scene;
        private Drawer drawer;
        private final Light light;
        private final Canvas canvas;
        private ReadyListener listener;
        private volatile boolean isDrawing;

        public RenderThread(Scene scene, Drawer drawer, Light light, Canvas canvas, ReadyListener listener) {
            this.scene = scene;
            this.drawer = drawer;
            this.light = light;
            this.canvas = canvas;
            this.listener = listener;
        }

        public void draw() {
            if (!isDrawing) {
                isDrawing = true;
                notify();
            }
        }

        @Override
        public void run() {
            while (true) {
                RayTracer.trace(light, scene);
                drawer.draw(light, canvas);
                listener.onRendered();
                try {
                    isDrawing = false;
                    this.wait();
                } catch (InterruptedException e) {
                    //ignore
                }
            }

        }

        interface ReadyListener {
            void onRendered();
        }
    }
}