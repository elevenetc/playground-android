package su.levenetc.androidplayground.utils;

import android.graphics.Canvas;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class CanvasGrid {

	private static final CanvasGrid inst = new CanvasGrid();

	public int centerX;
	public int centerY;
	public int leftCenterX;
	public int rightCenterX;

	private CanvasGrid() {

	}

	public static CanvasGrid build(Canvas canvas) {
		inst.centerX = canvas.getWidth() / 2;
		inst.centerY = canvas.getHeight() / 2;
		inst.leftCenterX = inst.centerX / 2;
		inst.rightCenterX = inst.centerX + inst.leftCenterX;
		return inst;
	}
}
