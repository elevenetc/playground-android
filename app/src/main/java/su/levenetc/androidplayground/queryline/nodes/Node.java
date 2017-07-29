package su.levenetc.androidplayground.queryline.nodes;

import android.graphics.Canvas;
import android.graphics.Rect;
import su.levenetc.androidplayground.queryline.QueryModel;
import su.levenetc.androidplayground.queryline.drawers.NodeDrawer;

public abstract class Node {

	protected StringBuilder text = new StringBuilder();
	protected Rect bounds = new Rect();
	protected QueryModel queryModel;
	private NodeDrawer drawer;

	public Node() {

	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

	public void setDrawer(NodeDrawer drawer) {
		this.drawer = drawer;
	}

	@Override public String toString() {
		return text.toString();
	}

	public void append(String value) {
		text.append(value);
	}

	public void setText(Node node) {
		setText(node.toString());
	}

	public void setText(String value) {
		text.setLength(0);
		text.append(value);
	}

	public void draw(Canvas canvas) {
		drawer.draw(canvas);
	}

	public boolean isEmpty() {
		return text.length() == 0;
	}

	public float getWidth() {
		return bounds.width();
	}

	public Rect bounds() {
		return bounds;
	}
}
