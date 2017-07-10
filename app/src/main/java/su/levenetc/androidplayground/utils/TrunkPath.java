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

		for (int i = 0; i <= size; i++) {

			final float ratio = MathUtils.sinValue(size, i);
			stepDeflection = ratio * diff;
			Out.pln("stepDeflection", stepDeflection);

			PathStep step = steps.get(i);

			path.lineTo(
					step.x + stepDeflection * controlX,
					step.y + stepDeflection * controlY
			);
		}

		for (int i = size; i > 0; i--) {
			PathStep step = steps.get(i);
			stepDeflection = MathUtils.sinValue(size, i) * diff;

			path.lineTo(
					step.x - stepDeflection * controlX,
					step.y - stepDeflection * controlY
			);
		}

		path.close();

		return path;
	}
}
