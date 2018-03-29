package su.levenetc.androidplayground.raytracer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayCasterView extends View {

    private Rect boundRect = new Rect();

    private Ray ray = new Ray();
    private Triangle triangle = new Triangle();

    public RayCasterView(Context context) {
        super(context);
    }

    public RayCasterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        boundRect.set(left, top, bottom, right);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLACK);


        int width = canvas.getWidth();
        int height = canvas.getHeight();
        double cx = width / 2;
        double cy = height / 2;

        triangle.init(cx, cy, cx + 100, cy + 100, cx + 200, cy);
        //triangle.translate(-100, -100);

        ray.init(cx, cy, 3000, 3000);

        RayTracer.trace(ray, boundRect);
        RayDrawer.draw(boundRect, canvas);
        RayDrawer.draw(ray, canvas);
        RayDrawer.draw(triangle, canvas);
    }
}
