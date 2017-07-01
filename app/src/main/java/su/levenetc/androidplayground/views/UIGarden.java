package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import su.levenetc.androidplayground.utils.BezierBuilder;
import su.levenetc.androidplayground.utils.CanvasGrid;
import su.levenetc.androidplayground.utils.Paints;
import su.levenetc.androidplayground.utils.SequenceCaller;

import java.util.List;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class UIGarden extends View {

	private PathFrame pathFrame;
	private SequenceCaller sequenceCaller;
	private Path path = new Path();

	public UIGarden(Context context) {
		super(context);
		init();
	}

	public UIGarden(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		sequenceCaller = new SequenceCaller(this);

	}

	@Override protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		sequenceCaller.start();
		final int width = getWidth();
	}

	@Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		final int width = getWidth();
		final int height = getHeight();
		final List<BezierBuilder.Step> points = new BezierBuilder(
				100, 100,
				900, 900,
				300, 50,
				50
		).get();

		path.moveTo(100, 100);

		AccelerateDecelerateInterpolator inter = new AccelerateDecelerateInterpolator();

		for (BezierBuilder.Step step : points) {
			final float x = step.xValue;
			final float y = step.yValue;
			final float stepTime = step.step;
			final float interpolatedStepTime = inter.getInterpolation(stepTime);
			final long time = (long) (interpolatedStepTime * 100);
			sequenceCaller.add(() -> {
				path.lineTo(x, y);
				invalidate();

			}, time);
		}


		sequenceCaller.start();
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), Paints.Fill.Red);

		final CanvasGrid grid = CanvasGrid.build(canvas);

//		if (pathFrame == null) {
//			pathFrame = new PathFrame(grid.leftCenterX, grid.centerY);
//		}
//
//		path.reset();
//
//		path.moveTo(grid.leftCenterX, grid.centerY);
//		path.lineTo(grid.rightCenterX, grid.centerY);

		canvas.drawPath(path, Paints.Stroke.Blue);
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
