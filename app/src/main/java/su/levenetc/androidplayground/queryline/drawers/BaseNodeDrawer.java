package su.levenetc.androidplayground.queryline.drawers;

import android.graphics.Canvas;
import su.levenetc.androidplayground.queryline.QueryModel;
import su.levenetc.androidplayground.queryline.nodes.Node;
import su.levenetc.androidplayground.utils.Paints;

public abstract class BaseNodeDrawer<N extends Node> implements NodeDrawer {

	protected N node;
	private QueryModel queryModel;

	public void setNode(N node) {
		this.node = node;
	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

	@Override public void measureLayoutDraw(Canvas canvas) {

		measureText();

		canvas.save();
		canvas.translate(queryModel.getXShift(node), 0);

		drawText(canvas);

		canvas.restore();
	}

	/**
	 * Default implementation
	 */
	void drawText(Canvas canvas) {
		canvas.drawText(node.toString(), 0, node.bounds().height(), Paints.Font.Black_26);
	}

	/**
	 * Default implementation
	 */
	void measureText() {
		final String string = node.toString();
		Paints.Font.Black_26.getTextBounds(string, 0, string.length(), node.bounds());
	}
}
