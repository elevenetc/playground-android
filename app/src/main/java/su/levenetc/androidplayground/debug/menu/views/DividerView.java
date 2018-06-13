package su.levenetc.androidplayground.debug.menu.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;

public class DividerView extends View {

    private final float heightDp = 10;
    private final Paint paint = new Paint();

    public DividerView(Context context) {
        super(context);
        paint.setStrokeWidth(dpToPx(3));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.drawLine(0, height / 2, width, height / 2, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        float height = dpToPx(heightDp);

        setMeasuredDimension(
                MeasureSpec.getSize(widthMeasureSpec),
                (int) height
        );
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
