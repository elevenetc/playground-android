package su.levenetc.androidplayground.mergeview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by eugene.levenetc on 16/06/2017.
 */
public class PairView {

	public int leftIndex;
	public int rightIndex;
	public Path path = new Path();

	Paint rectColor = new Paint();
	Paint pathColor = new Paint();

	private float leftHeight = -1;
	private float rightHeight = -1;
	private int leftFirstVisible;
	private int leftLastVisible;
	private int rightFirstVisible;
	private int rightLastVisible;

	public PairView(int leftIndex, int rightIndex) {

		this.leftIndex = leftIndex;
		this.rightIndex = rightIndex;

		rectColor.setStyle(Paint.Style.FILL);
		rectColor.setColor(Color.BLUE);

		pathColor.setStrokeWidth(5);
		pathColor.setStyle(Paint.Style.FILL);
		pathColor.setColor(Color.RED);
	}

	void drawConnection(View leftView, View rightView, Canvas canvas) {

		if (leftHeight == -1 && leftView != null) leftHeight = leftView.getHeight();
		if (rightHeight == -1 && rightView != null) rightHeight = rightView.getHeight();

		//drawRect(leftView, rightView, canvas);
		drawPath(leftView, rightView, canvas);
	}

	void drawRect(View leftView, View rightView, Canvas canvas) {

		if (leftView != null) {
			final float top = leftView.getY();
			drawRect(0, top, top + leftView.getHeight(), canvas);
		}

		if (rightView != null) {
			final float top = rightView.getY();
			drawRect(canvas.getWidth() / 2, top, top + rightView.getHeight(), canvas);
		}

	}

	void drawPath(View leftView, View rightView, Canvas canvas) {

		final float cWidth = canvas.getWidth();
		final float cHeight = canvas.getHeight();

		float xLeft = 0;
		float yLeft;
		float xRight = cWidth;
		float yRight;

		float xLeftControl = cWidth / 2f;
		float yLeftControl;

		float xRightControl = cWidth / 2f;
		float yRightControl;

		if (leftView != null && rightView != null) {
			yLeft = leftView.getY();
			yRight = rightView.getY();
			yLeftControl = yLeft;
			yRightControl = yRight;
		} else {

			if (leftView != null) {
				yLeft = leftView.getY();
				yRight = rightIndex <= rightFirstVisible ? -rightHeight : cHeight;
				yLeftControl = yLeft;
				yRightControl = yRight;
			} else if (rightView != null) {
				yLeft = leftIndex <= leftFirstVisible ? -leftHeight : cHeight;
				yRight = rightView.getY();
				yLeftControl = yLeft;
				yRightControl = yRight;
			} else {
				yLeft = leftIndex <= leftFirstVisible ? -leftHeight : cHeight;
				yRight = rightIndex <= rightFirstVisible ? -rightHeight : cHeight;
				yLeftControl = yLeft;
				yRightControl = yRight;
			}
		}

		path.reset();
		buildTopPath(xLeft, yLeft, xRight, yRight, xLeftControl, yLeftControl, xRightControl, yRightControl);
		path.rLineTo(0, rightHeight);
		buildBottomPath(xLeft, yLeft + leftHeight, xLeftControl, yLeftControl + leftHeight, xRightControl, yRightControl + rightHeight);
		path.close();
		canvas.drawPath(path, pathColor);
	}

	void buildTopPath(float xLeft, float yLeft,
	                  float xRight, float yRight,
	                  float xLeftControl, float yLeftControl,
	                  float xRightControl, float yRightControl) {

		path.moveTo(xLeft, yLeft);
		path.cubicTo(xLeftControl, yLeftControl, xRightControl, yRightControl, xRight, yRight);

	}

	void buildBottomPath(float xLeft, float yLeft,
	                     float xLeftControl, float yLeftControl,
	                     float xRightControl, float yRightControl) {

		path.cubicTo(xRightControl, yRightControl, xLeftControl, yLeftControl, xLeft, yLeft);

	}

	void drawRect(float left, float top, float bottom, Canvas canvas) {
		canvas.drawRect(left, top, left + canvas.getWidth() / 2, bottom, rectColor);
	}

	public void setVisible(int leftFirstVisible, int leftLastVisible, int rightFirstVisible, int rightLastVisible) {
		this.leftFirstVisible = leftFirstVisible;
		this.leftLastVisible = leftLastVisible;
		this.rightFirstVisible = rightFirstVisible;
		this.rightLastVisible = rightLastVisible;
	}
}
