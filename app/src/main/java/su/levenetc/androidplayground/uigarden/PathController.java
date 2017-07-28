package su.levenetc.androidplayground.uigarden;

import android.graphics.Canvas;
import android.graphics.Path;
import android.view.animation.AccelerateDecelerateInterpolator;
import su.levenetc.androidplayground.utils.BezierCurveQuadratic;
import su.levenetc.androidplayground.utils.Paints;
import su.levenetc.androidplayground.utils.PathStep;
import su.levenetc.androidplayground.utils.SequenceCaller;

import java.util.List;

/**
 * Created by eugene.levenetc on 10/07/2017.
 * Builds and animates
 */
public class PathController {

	private SequenceCaller sequenceCaller;
	private UIGarden garden;
	private Path path = new Path();
	AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();

	float startX = 50;
	float startY = 50;
	int steps = 25;
	long baseTime = 2500;
	private BezierCurveQuadratic curve;

	public PathController(UIGarden garden) {

		this.garden = garden;
	}

	public void init() {
		buildPoints();
	}

	public void reset() {
		path.reset();
		buildPoints();
	}

	public void startDraw() {
		sequenceCaller.start();
	}

	public Path getPath() {
		return path;
	}

	private void buildPoints() {

		final int width = garden.getWidth();
		final int height = garden.getHeight();

		sequenceCaller = new SequenceCaller(garden);
		curve = new BezierCurveQuadratic(
				startX, startY,
				width - startX, height - startY,
				width / 2, 50,
				steps
		);
		final List<PathStep> points = curve.getSteps();

		path.moveTo(startX, startY);

		float timeDiff = 0;
		float prevTime = 0;

		TrunkPath trunkPath = new TrunkPath(curve);

		for (int i = 0; i < points.size(); i++) {
			PathStep step = points.get(i);
			final float x = step.x;
			final float y = step.y;
			final float stepTime = step.step;
			final float interpolatedStepTime = interpolator.getInterpolation(stepTime);

			float intTime = interpolatedStepTime;

			if (prevTime == 0) {
				prevTime = intTime;
			} else {
				timeDiff = intTime - prevTime;
				prevTime = intTime;
			}

			long timeResult = (long) (timeDiff * baseTime);

			final int pos = i;

			sequenceCaller.add(() -> {
				path = trunkPath.get(pos);
				//path.lineTo(x, y);
				garden.invalidate();

			}, timeResult);
		}

	}

	public void drawDebug(Canvas canvas) {
		curve.drawDebug(canvas);

		canvas.drawRect(startX, startY,
				canvas.getWidth() - startX, canvas.getHeight() - startY,
				Paints.Stroke.Red
		);
	}
}
