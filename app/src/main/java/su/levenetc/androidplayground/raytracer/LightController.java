package su.levenetc.androidplayground.raytracer;

import android.graphics.Canvas;
import android.view.MotionEvent;

import su.levenetc.androidplayground.utils.Paints;

public class LightController {

    public ConeLight light;
    private float locRadius = 50;
    private float dirRadius = 30;
    private boolean dragLocation;
    private boolean dragDirection;

    public LightController(ConeLight light) {
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
            } else if (isAtDirection(x, y)) {
                dragDirection = true;
                light.updateDirection(x, y);
                return true;
            }
        } else if (action == MotionEvent.ACTION_MOVE && (dragLocation || dragDirection)) {

            if (dragLocation) {
                light.updatePosition(x, y);
                return true;
            } else if (dragDirection) {
                light.updateDirection(x, y);
                return true;
            }
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            dragLocation = false;
            dragDirection = false;
            return true;
        }

        return false;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) light.x, (float) light.y, locRadius, Paints.Stroke.RedBold);
        canvas.drawCircle((float) light.dirX, (float) light.dirY, dirRadius, Paints.Stroke.RedBold);
        canvas.drawLine((float) light.x, (float) light.y, (float) light.dirX, (float) light.dirY, Paints.Stroke.Red);
    }

    private boolean isAtLocation(float x, float y) {
        float bound = locRadius / 2;
        return (x > (light.x - bound) && x < (light.x + bound))
                && (y > (light.y - bound) && y < (light.y + bound));
    }

    private boolean isAtDirection(float x, float y) {
        float bound = dirRadius / 2;
        return (x > (light.dirX - bound) && x < (light.dirX + bound))
                && (y > (light.dirY - bound) && y < (light.dirY + bound));
    }
}