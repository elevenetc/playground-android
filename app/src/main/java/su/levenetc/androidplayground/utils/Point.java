package su.levenetc.androidplayground.utils;

/**
 * Created by eugene.levenetc on 10/07/2017.
 */
public class Point {

	float x;
	float y;

	public Point(float x, float y) {

		this.x = x;
		this.y = y;
	}

	public void addToX(float value) {
		x += value;
	}

	public void addToY(float value) {
		y += value;
	}

	public void set(Point point) {
		this.x = point.x;
		this.y = point.y;


	}

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Point point = (Point) o;

		if (Float.compare(point.x, x) != 0) return false;
		return Float.compare(point.y, y) == 0;
	}

	@Override public int hashCode() {
		int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
		return result;
	}

	public void fixZeroes() {
		if (x == -0.0f) x = 0.0f;
		if (y == -0.0f) y = 0.0f;
	}
}
