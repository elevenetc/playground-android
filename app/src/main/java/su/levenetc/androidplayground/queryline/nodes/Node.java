package su.levenetc.androidplayground.queryline.nodes;

import android.graphics.Rect;
import su.levenetc.androidplayground.queryline.QueryModel;

public abstract class Node {

	protected StringBuilder text = new StringBuilder();
	protected Rect bounds = new Rect();
	protected QueryModel queryModel;

	public Node() {

	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

	@Override public String toString() {
		return text.toString();
	}

	public void append(char value) {
		text.append(value);
	}

	public void setText(Node node) {
		setText(node.toString());
	}

	public void setText(String value) {
		text.setLength(0);
		text.append(value);
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
