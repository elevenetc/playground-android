package su.levenetc.androidplayground.utils;

import android.graphics.Canvas;

import java.util.List;

/**
 * Created by eugene.levenetc on 10/07/2017.
 */
public class DebugDraw {

	public static void drawPoints(List<Point> points, Canvas canvas) {
		for (Point point : points)
			canvas.drawCircle(point.x, point.y, Values.DP_3, Paints.Stroke.Green);
	}

	public static void drawLine(List<Point> points, Canvas canvas) {
		Point prev = null;
		for (Point point : points) {
			if (prev == null) {
				prev = point;
			} else {
				canvas.drawLine(prev.x, prev.y, point.x, point.y, Paints.Stroke.Green);
				prev = point;
			}
		}
	}

	public static void draw(Canvas canvas) {

	}
}
