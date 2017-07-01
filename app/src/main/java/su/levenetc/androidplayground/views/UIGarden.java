package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
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
		final List<PointF> points = new BezierBuilder(
				100, 100,
				900, 900,
				300, 50,
				10
		).get();

		path.moveTo(100, 100);

		for (PointF point : points) {
			final float x = point.x;
			final float y = point.y;
			sequenceCaller.add(() -> {
				path.lineTo(x, y);
				invalidate();
			}, 500);
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
