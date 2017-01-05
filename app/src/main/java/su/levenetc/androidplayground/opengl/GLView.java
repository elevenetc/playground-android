package su.levenetc.androidplayground.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
public class GLView extends GLSurfaceView {

    private GLRenderer renderer;
    private RotationHandler rotationHandler;
    private DragHandler dragHandler;
    private TestHandler testHandler;
    private float prevX;
    private float prevY;
    private float downX;
    private float downY;

    public GLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GLView(Context context) {
        super(context);
        init(context);
    }

    public GLRenderer getRenderer() {
        return renderer;
    }

    private void init(Context context) {
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        renderer = new GLRenderer(context);

        rotationHandler = new RotationHandler(renderer);
        dragHandler = new DragHandler(renderer);
        testHandler = new TestHandler(renderer);

        setRenderer(renderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        final float x = e.getX();
        final float y = e.getY();
        final int action = e.getAction();


        if (action == MotionEvent.ACTION_MOVE) {
            final int width = getWidth();
            final int height = getHeight();
            rotationHandler.onMove(prevX, prevY, x, y, width, height);
            dragHandler.onMove(prevX, prevY, x, y, width, height);
            testHandler.onMove(downX, downY, prevX, prevY, x, y, width, height);
            requestRender();
        } else if (action == MotionEvent.ACTION_DOWN) {
            downX = x;
            downY = y;
        }

        prevX = x;
        prevY = y;

        return true;
    }

    private static class TestHandler {

        private final GLRenderer renderer;

        public TestHandler(GLRenderer renderer) {
            this.renderer = renderer;
        }

        void onMove(float downX, float downY,
                    float prevX, float prevY,
                    float x, float y,
                    float width, float height) {
            float hw = width / 2;
            float hh = height / 2;
            float xVal;
            float yVal;
            if (x > hw) {
                xVal = (x - hw) / hw;
            } else {
                xVal = (1 - x / hw) * -1;
            }

            if (y > hh) {
                yVal = (y - hh) / hh;
            } else {
                yVal = (1 - y / hh) * -1;
            }
            renderer.setTestValues(xVal, yVal);
        }
    }

    private static class RotationHandler {

        private static final float TOUCH_SCALE_FACTOR = 180.0f / 320;

        private final GLRenderer renderer;

        public RotationHandler(GLRenderer renderer) {
            this.renderer = renderer;
        }

        void onMove(float prevX, float prevY, float x, float y, float width, float height) {
            float dx = x - prevX;
            float dy = y - prevY;

            // reverse direction of rotation above the mid-line
            if (y > height / 2) {
                dx = dx * -1;
            }

            // reverse direction of rotation to left of the mid-line
            if (x < width / 2) {
                dy = dy * -1;
            }

            renderer.setAngle(renderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320

        }
    }

    private static class DragHandler {

        private final GLRenderer renderer;

        public DragHandler(GLRenderer renderer) {
            this.renderer = renderer;
        }

        void onMove(float prevX, float prevY, float x, float y, float width, float height) {
            renderer.updateCameraLocation(x / 100f, y / 100f);
//            renderer.updateCameraLocation((x - prevX) / 100f, (y - prevY) / 100f);
        }
    }

}
