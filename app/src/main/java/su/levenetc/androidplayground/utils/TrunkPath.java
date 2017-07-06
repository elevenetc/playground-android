package su.levenetc.androidplayground.utils;

import android.graphics.Path;

import java.util.List;

/**
 * Created by eugene.levenetc on 02/07/2017.
 */
public class TrunkPath {

	BezierCurve curve;
	Path path = new Path();
	private float diff = 50;

	public TrunkPath(BezierCurve curve) {
		this.curve = curve;
	}

	public Path get(final int size) {
		if (size <= 2) return path;

		path.reset();
		final List<PathStep> steps = curve.getSteps();

		final PathStep start = steps.get(0);
		float stepDeflection = 0;
		final float controlX = curve.getControlX() / 300;
		final float controlY = curve.getControlY() / 300;

		path.moveTo(start.x, start.y);

		for (int i = 1; i < size; i++) {
			PathStep step = steps.get(i);

			path.lineTo(
					step.x + stepDeflection * controlX,
					step.y + stepDeflection * controlY
			);

			stepDeflection = MathUtils.sinValue(i, size) * diff;
		}

		stepDeflection = 0;

		for (int i = size - 1; i >= 0; i--) {
			PathStep step = steps.get(i);
			path.lineTo(
					step.x - stepDeflection * controlX,
					step.y - stepDeflection * controlY
			);

			stepDeflection = MathUtils.sinValue(i, size) * diff;
		}

		return path;
	}
}
