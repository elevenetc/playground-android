package su.levenetc.androidplayground.queryline.nodes;

import android.graphics.Rect;
import su.levenetc.androidplayground.queryline.QueryModel;

public abstract class Node {

	protected StringBuilder currentText = new StringBuilder();
	protected Rect bounds = new Rect();
	protected QueryModel queryModel;

	public Node() {

	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

	@Override public String toString() {
		return currentText.toString();
	}

	public void append(char value) {
		currentText.append(value);
	}

	public void setCurrentText(Node node) {
		setText(node.toString());
	}

	public void setText(String value) {
		currentText.setLength(0);
		currentText.append(value);
	}

	public boolean isEmpty() {
		return currentText.length() == 0;
	}

	public float getWidth() {
		return bounds.width();
	}

	public Rect bounds() {
		return bounds;
	}
}
