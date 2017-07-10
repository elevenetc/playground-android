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

	public void addToX(float value){
		x += value;
	}

	public void addToY(float value){
		y += value;
	}
}
