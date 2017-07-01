package su.levenetc.androidplayground.utils;

import android.graphics.Path;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class PathBuilder {
	private Path path;

	public PathBuilder(Path path) {

		this.path = path;
	}

	public PathBuilder from(int x, int y) {
		path.moveTo(x, y);
		return this;
	}

	public PathBuilder diff(int xDiff, int yDiff) {
		path.rLineTo(xDiff, yDiff);
		return this;
	}

	public PathBuilder to(int x, int y) {
		path.lineTo(x, y);
		return this;
	}

	public Path finish() {
		return path;
	}
}
