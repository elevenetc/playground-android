package su.levenetc.androidplayground.utils;

/**
 * Created by eugene.levenetc on 10/07/2017.
 */
public class Vector {

	private final Point start;
	private final Point end;
	private float length = -1;

	public Vector(Point start, Point end) {

		this.start = start;
		this.end = end;
	}

	public float length() {
		if (length == -1)
			length = (float) Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.x - start.x, 2));

		return length;
	}

	/**
	 * @param t (0 end 1) - on vector, (<0) - out from start (>1) - out from end
	 * @return
	 */
	public Point getPointAt(float t) {
		final float x = (1 - t) * start.x + t * end.x;
		final float y = (1 - t) * start.y + t * end.y;
		return new Point(x, y);
	}
}