package su.levenetc.androidplayground.raytracer.lights;

import android.graphics.Canvas;
import android.view.MotionEvent;

import su.levenetc.androidplayground.utils.Paints;

public class UndirectedLightController implements LightController {

    private float locRadius = 50;
    private boolean dragLocation;
    public Light light;

    public UndirectedLightController(Light light) {
        this.light = light;
    }

    public boolean onTouch(MotionEvent event) {
        int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        if (action == MotionEvent.ACTION_DOWN) {
            if (isAtLocation(x, y)) {
                dragLocation = true;
                light.updatePosition(x, y);
                return true;
            }
        } else if (action == MotionEvent.ACTION_MOVE && dragLocation) {
            light.updatePosition(x, y);
            return true;
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            dragLocation = false;
            return true;
        }

        return false;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) light.x, (float) light.y, locRadius, Paints.Stroke.RedBoldAlpha50);
    }

    private boolean isAtLocation(float x, float y) {
        float bound = locRadius / 2;
        return (x > (light.x - bound) && x < (light.x + bound))
                && (y > (light.y - bound) && y < (light.y + bound));
    }
}