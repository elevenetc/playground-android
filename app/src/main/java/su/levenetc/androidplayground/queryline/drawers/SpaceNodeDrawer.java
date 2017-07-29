package su.levenetc.androidplayground.queryline.drawers;

import android.graphics.Canvas;
import android.graphics.Rect;
import su.levenetc.androidplayground.queryline.nodes.SpaceNode;
import su.levenetc.androidplayground.utils.Paints;

public class SpaceNodeDrawer extends BaseNodeDrawer<SpaceNode> {

	@Override void drawText(Canvas canvas) {
		super.drawText(canvas);
		canvas.drawRect(node.bounds(), Paints.Stroke.GreenBold);
	}

	@Override void measureText() {
		final Rect bounds = node.bounds();
		final float width = Paints.Font.Black_26.measureText(" ");
		bounds.bottom = 10;
		bounds.right = (int) width;
	}
}
