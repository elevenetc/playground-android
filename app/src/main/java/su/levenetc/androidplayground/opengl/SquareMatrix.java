package su.levenetc.androidplayground.opengl;

import android.content.Context;

import su.levenetc.androidplayground.utils.Utils;

/**
 * Created by eugene.levenetc on 31/12/2016.
 */

public class SquareMatrix {

    private Square[] squares;

    public SquareMatrix(int width, int height, Context context) {
        squares = new Square[width * height];
        int i = 0;
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                squares[i] = new Square(0.5f, 0.5f, w * 0.5f, h * 0.5f, Utils.randomColor(), context);
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
