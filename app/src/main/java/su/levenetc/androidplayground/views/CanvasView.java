package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.Matrix;
import android.view.View;
import su.levenetc.androidplayground.utils.Mat;

/**
 * Created by eugene.levenetc on 27/12/2016.
 */
public class CanvasView extends View {


	private final Paint whiteFillPaint = new Paint();
	private final float[] matrix;
	private final int[] colors;
	private float rot;
	private final Mat rightTop;
	private final Mat rightBottom;
	private final Mat leftBottom;
	private final Mat leftTop;

	public CanvasView(Context context) {
		super(context);

		whiteFillPaint.setColor(Color.GREEN);
		whiteFillPaint.setStyle(Paint.Style.FILL);
		whiteFillPaint.setStrokeWidth(50);

		matrix = new float[16];

		colors = new int[]{
				Color.GREEN,
				Color.GREEN,
				Color.GREEN
		};
		Matrix.setIdentityM(matrix, 0);

		rightTop = new Mat(1, 1, 0);
		rightBottom = new Mat(1, -1, 0);
		leftBottom = new Mat(-1, -1, 0);
		leftTop = new Mat(-1, 1, 0);
	}

	@Override protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.RED);

		final int width = canvas.getWidth();
		final int height = canvas.getHeight();
		final int size = 200;

		final int centerX = 0;
		final int centerY = 0;

		final boolean hardwareAccelerated = isHardwareAccelerated();
		final boolean hardwareAccelerated1 = canvas.isHardwareAccelerated();

		//canvas.drawVertices(Canvas.VertexMode.TRIANGLES, 3, vertices, 0, null, 0, colors, 0, null, 0, 0, whiteFillPaint);
		canvas.translate(300, 300);
		float mult = 100;
		canvas.drawPoint(rightTop.getX() * mult, rightTop.getY() * mult, whiteFillPaint);
		canvas.drawPoint(rightBottom.getX() * mult, rightBottom.getY() * mult, whiteFillPaint);
		canvas.drawPoint(leftBottom.getX() * mult, leftBottom.getY() * mult, whiteFillPaint);
		canvas.drawPoint(leftTop.getX() * mult, leftTop.getY() * mult, whiteFillPaint);

		//canvas.setMatrix(canvas.getMatrix());
		//canvas.drawRect(centerX - size, centerY - size, centerX + size, centerY + size, whiteFillPaint);
		//postInvalidateDelayed(60 / 1000);
	}
}
