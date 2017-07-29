package su.levenetc.androidplayground.queryline.drawers;

import android.graphics.Canvas;
import android.graphics.Rect;
import su.levenetc.androidplayground.queryline.nodes.AutoCompleteNode;
import su.levenetc.androidplayground.utils.Paints;

public class AutoCompleteDrawer extends BaseNodeDrawer<AutoCompleteNode> {

	@Override void drawText(Canvas canvas) {
		final Rect bounds = node.bounds();
		final String text = node.toString();

		canvas.drawText(text, 0, node.bounds().height(), Paints.Font.Black_26);

		if (node.autoComplete.length() > 0) {
			final CharSequence complete = node.autoComplete.subSequence(text.length(), node.autoComplete.length());
			canvas.drawText(String.valueOf(complete), node.bounds().width(), bounds.height(), Paints.Font.Black_26_Alpha_50);
		}
	}
}
