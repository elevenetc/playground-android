package su.levenetc.androidplayground.raycaster;

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

    private EnvModel model = new EnvModel();

    private Ray ray = new Ray();

    public RayCasterView(Context context) {
        super(context);
    }

    public RayCasterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        model.set(left, top, bottom, right);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLACK);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        double cx = width / 2;
        double cy = height / 2;

        ray.initVector.set(cx, cy, 3000, 3000);

        RayCaster.cast(ray, model);
        RayDrawer.draw(ray, canvas);
    }
}
