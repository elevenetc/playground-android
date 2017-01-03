package su.levenetc.androidplayground.opengl;

import android.content.Context;
import su.levenetc.androidplayground.utils.Utils;

/**
 * Created by eugene.levenetc on 31/12/2016.
 */

public class SquareMatrix {

	private Square[] squares;

	public SquareMatrix(float width, float height, float size, Context context) {
		squares = new Square[(int) (width * height)];
		int i = 0;
		for (float w = 0; w < width; w++) {
			for (float h = 0; h < height; h++) {
				final float xTrans = w * size;
				final float yTrans = h * size;
				squares[i] = new Square(
						size, size,
						xTrans, yTrans,
						Utils.randomColor(),
						context);
				i++;
			}
		}
	}

	public void draw(float[] mvpMatrix) {
		for (int i = 0; i < squares.length; i++) {
			squares[i].draw(mvpMatrix);
		}
	}
}