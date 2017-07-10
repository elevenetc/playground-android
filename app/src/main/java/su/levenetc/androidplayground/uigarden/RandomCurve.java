package su.levenetc.androidplayground.uigarden;

import android.graphics.Canvas;
import su.levenetc.androidplayground.utils.DebugDraw;
import su.levenetc.androidplayground.utils.Point;
import su.levenetc.androidplayground.utils.Utils;
import su.levenetc.androidplayground.utils.Vector;

import java.util.LinkedList;

/**
 * Created by eugene.levenetc on 10/07/2017.
 */
public class RandomCurve {

	private final float fromX;
	private final float fromY;
	private final float toX;
	private final float toY;
	private final int breakPoints;

	Vector vector;
	LinkedList<Point> points = new LinkedList<>();

	public RandomCurve(
			float fromX, float fromY,
			float toX, float toY,
			int breakPoints,
			float bias
	) {

		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.breakPoints = breakPoints;

		vector = new Vector(new Point(fromX, fromY), new Point(toX, toY));

		final float length = vector.length();
		final int segments = breakPoints + 1;
		final float segmentLength = length / segments;

		float step = 1f / segments;
		float currentStep = step;
		while (breakPoints > 0) {

			final Point point = vector.getPointAt(currentStep);
			point.addToX(Utils.randomInRangeSigned(0, bias) * segmentLength);
			point.addToY(Utils.randomInRangeSigned(0, bias) * segmentLength);


			points.add(point);

			currentStep += step;
			breakPoints--;
		}

		points.addFirst(new Point(fromX, fromY));
		points.addLast(new Point(toX, toY));
	}

	public void debugDraw(Canvas canvas) {
		DebugDraw.drawLine(points, canvas);
		DebugDraw.drawPoints(points, canvas);
	}
}