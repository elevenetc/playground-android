package su.levenetc.androidplayground.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class BezierBuilder {

	private final float fromX;
	private final float fromY;
	private final float toX;
	private final float toY;
	private final float controlX;
	private final float controlY;
	private final int steps;

	public BezierBuilder(
			float fromX, float fromY,
			float toX, float toY,
			float controlX, float controlY,
			int steps
	) {

		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.controlX = controlX;
		this.controlY = controlY;
		this.steps = steps;
	}

	public List<Step> get() {
		float t = 0;
		List<Step> result = new LinkedList<>();
		for (int i = 0; i < steps; i++) {
			t += 1f / steps;
			float x = (1 - t) * (1 - t) * fromX + 2 * (1 - t) * t * controlX + t * t * toX;
			float y = (1 - t) * (1 - t) * fromY + 2 * (1 - t) * t * controlY + t * t * toY;

			result.add(new Step(x, y, t));
		}
		return result;
	}

	public static class Step {
		public float xValue;
		public float yValue;
		public float step;

		public Step(float xValue, float yValue, float step) {
			this.xValue = xValue;
			this.yValue = yValue;
			this.step = step;
		}
	}
}
