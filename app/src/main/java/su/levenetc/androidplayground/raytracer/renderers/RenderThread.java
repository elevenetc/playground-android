package su.levenetc.androidplayground.raytracer.renderers;

import android.graphics.Canvas;
import android.graphics.Color;

import su.levenetc.androidplayground.raytracer.Scene;
import su.levenetc.androidplayground.raytracer.drawers.Drawer;
import su.levenetc.androidplayground.raytracer.lights.Light;
import su.levenetc.androidplayground.raytracer.tracers.RayTracer;

public class RenderThread extends Thread {

    private RayTracer tracer;
    private Scene scene;
    private Drawer drawer;
    private final Light light;
    private final Canvas canvas;
    private ReadyListener listener;
    private volatile boolean isDrawing;

    private final Object lock = new Object();

    public RenderThread(
            RayTracer tracer,
            Scene scene,
            Drawer drawer,
            Light light,
            Canvas canvas,
            ReadyListener listener) {
        this.tracer = tracer;
        this.scene = scene;
        this.drawer = drawer;
        this.light = light;
        this.canvas = canvas;
        this.listener = listener;
    }

    public boolean isDrawing() {
        return isDrawing;
    }

    public void draw() {
        if (!isDrawing) {
            isDrawing = true;
            synchronized (lock) {
                lock.notify();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            tracer.trace(light, scene);
            canvas.drawColor(Color.BLACK);
            drawer.draw(light, canvas);
            listener.onRendered();
            try {
                isDrawing = false;
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                //ignore
            }
        }

    }

}
