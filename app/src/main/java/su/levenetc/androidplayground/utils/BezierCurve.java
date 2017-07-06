package su.levenetc.androidplayground.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class BezierCurve {

	private final float fromX;
	private final float fromY;
	private final float toX;
	private final float toY;
	private final float controlX;
	private final float controlY;
	private final int stepsAmount;

	List<PathStep> steps = new LinkedList<>();

	public BezierCurve(
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

		for (int i = 0; i < stepsAmount; i++) {
			t += 1f / stepsAmount;
			float x = (1 - t) * (1 - t) * fromX + 2 * (1 - t) * t * controlX + t * t * toX;
			float y = (1 - t) * (1 - t) * fromY + 2 * (1 - t) * t * controlY + t * t * toY;

			steps.add(new PathStep(x, y, t));
		}
	}

}
