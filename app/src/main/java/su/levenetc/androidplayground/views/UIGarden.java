package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import su.levenetc.androidplayground.utils.*;

import java.util.List;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class UIGarden extends View {

	private PathFrame pathFrame;
	private SequenceCaller sequenceCaller;
	private Path path = new Path();

	float startX = 50;
	float startY = 50;
	int steps = 25;
	long baseTime = 2500;

	AccelerateDecelerateInterpolator inter = new AccelerateDecelerateInterpolator();

	public UIGarden(Context context) {
		super(context);
		init();
	}

	public UIGarden(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {

	}

	@Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		buildPoints();
		startDraw();
	}

	private void buildPoints() {

		sequenceCaller = new SequenceCaller(this);

		final int width = getWidth();
		final int height = getHeight();

		final BezierCurve curve = new BezierCurve(
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
			final float interpolatedStepTime = inter.getInterpolation(stepTime);

			float intTime = interpolatedStepTime;
			float delay = 0;

			if (prevTime == 0) {
				prevTime = intTime;
			} else {
				timeDiff = intTime - prevTime;
				prevTime = intTime;
			}

			long timeResult = (long) (timeDiff * baseTime);

			//Log.d("delay", timeResult + "");

			final int pos = i;

			sequenceCaller.add(() -> {
				path = trunkPath.get(pos);
				//path.lineTo(x, y);
				invalidate();

			}, timeResult);
		}


	}

	private float[] points = new float[]{
			100, 100,
			150, 100,

			150, 100,
			150, 150,

			150, 150,
			100, 100
	};

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), Paints.Fill.Grey);
		canvas.drawPath(path, Paints.Stroke.White);

		canvas.drawLines(points, Paints.Fill.Red);

		canvas.drawRect(startX, startY,
				canvas.getWidth() - startX, canvas.getHeight() - startY,
				Paints.Stroke.Red
		);
	}

	public void restart() {
		path.reset();
		buildPoints();
		startDraw();
	}

	private void startDraw() {
		postDelayed(() -> sequenceCaller.start(), 500);
	}

	static class PathFrame {

		private Path path = new Path();

		public PathFrame(int x, int y) {
			path.moveTo(x, y);
		}

		public PathFrame add(int x, int y) {
			path.lineTo(x, y);
			return this;
		}

		public void onDraw(Canvas canvas) {
			canvas.drawPath(path, Paints.Stroke.Blue);
		}
	}
}
