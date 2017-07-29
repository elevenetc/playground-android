package su.levenetc.androidplayground.queryline.drawers;

import android.graphics.Canvas;
import android.graphics.Rect;
import su.levenetc.androidplayground.queryline.nodes.StaticNode;
import su.levenetc.androidplayground.utils.Paints;

public class StaticNodeDrawer extends BaseNodeDrawer<StaticNode> {

	@Override void drawText(Canvas canvas) {
		super.drawText(canvas);
		final Rect bounds = node.bounds();
		canvas.drawRect(0, 0, bounds.width(), bounds.height(), Paints.Stroke.Red);
	}
}
