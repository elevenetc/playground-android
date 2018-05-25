package su.levenetc.androidplayground.raytracer.lights;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface LightController {
    boolean onTouch(MotionEvent event);

    void draw(Canvas canvas);
}
