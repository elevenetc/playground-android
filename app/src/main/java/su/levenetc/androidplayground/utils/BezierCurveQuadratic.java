package su.levenetc.androidplayground.utils;

import android.graphics.Canvas;
import android.graphics.Path;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class BezierCurveQuadratic {

	private final float fromX;
	private final float fromY;
	private final float toX;
	private final float toY;
	private final float controlX;
	private final float controlY;
	private final int stepsAmount;

	List<PathStep> steps = new LinkedList<>();
	Path debugPath = new Path();

	public BezierCurveQuadratic(
			float fromX, float fromY,
			float toX, float toY,
			float controlX, float controlY,
			int stepsAmount
	) {

		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.controlX = controlX;
		this.controlY = controlY;
		this.stepsAmount = stepsAmount;

		build();
	}

	public List<PathStep> getSteps() {
		return steps;
	}

	public float getControlX() {
		return controlX;
	}

	public float getControlY() {
		return controlY;
	}

	private void build() {
		float t = 0;

		steps.add(new PathStep(fromX, fromY, 0));
		debugPath.moveTo(fromX, fromY);

		for (int i = 1; i < stepsAmount; i++) {
			t += 1f / stepsAmount;
			final float[] coords = DrawUtils.quadraticBezier(t, fromX, fromY, toX, toY, controlX, controlY);
			steps.add(new PathStep(coords[0], coords[1], t));
			debugPath.lineTo(coords[0], coords[1]);
		}

		debugPath.lineTo(toX, toY);

		steps.add(new PathStep(toX, toY, 1));
	}

	public void drawDebug(Canvas canvas) {
		canvas.drawCircle(fromX, fromY, Values.DP_5, Paints.Stroke.GreenBold);
		canvas.drawCircle(toX, toY, Values.DP_5, Paints.Stroke.GreenBold);
		canvas.drawCircle(controlX, controlY, Values.DP_5, Paints.Stroke.GreenBold);

		canvas.drawPath(debugPath, Paints.Stroke.GreenBold);

		canvas.drawLine(fromX, fromY, controlX, controlY, Paints.Stroke.Green);
		canvas.drawLine(toX, toY, controlX, controlY, Paints.Stroke.Green);
	}

}
