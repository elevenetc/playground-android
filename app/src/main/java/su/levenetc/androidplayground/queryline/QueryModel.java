package su.levenetc.androidplayground.queryline;

import android.graphics.Canvas;
import su.levenetc.androidplayground.queryline.drawers.DrawersFactory;
import su.levenetc.androidplayground.queryline.nodes.AutoCompleteNode;
import su.levenetc.androidplayground.queryline.nodes.Node;
import su.levenetc.androidplayground.queryline.nodes.StaticNode;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class QueryModel {

	Deque<Node> nodes = new LinkedList<>();
	private DrawersFactory drawersFactory;

	public QueryModel(DrawersFactory drawersFactory) {

		this.drawersFactory = drawersFactory;
	}

	public void append(String ch) {

		if (nodes.isEmpty()) {
			final AutoCompleteNode chars = drawersFactory.autoComplete();
			nodes.add(chars);
		}

		nodes.getLast().append(ch);

		if (nodes.getLast() instanceof AutoCompleteNode) {
			((AutoCompleteNode) nodes.getLast()).updateAutoComplete();
		}
	}

	public void convertToStatic(Node toConvert) {

		for (Node node : nodes) {
			if (toConvert == node) {
				nodes.remove(node);
				final StaticNode staticNode = drawersFactory.staticNode();
				staticNode.setText(node);
				nodes.addLast(staticNode);
				nodes.add(drawersFactory.space());
				nodes.add(drawersFactory.autoComplete());
				break;
			}
		}
	}

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Node node : nodes) {
			sb.append(node.toString());
		}
		return sb.toString();
	}

	public void draw(Canvas canvas) {
		for (Node node : nodes) node.draw(canvas);
	}

	public void deleteLast() {
		if (!nodes.isEmpty()) {
			final Node last = nodes.getLast();

			if (last.isEmpty()) {
				nodes.removeLast();
			} else {
				if (last instanceof AutoCompleteNode) {
					((AutoCompleteNode) last).deleteLast();
					((AutoCompleteNode) last).updateAutoComplete();
				} else if (last instanceof StaticNode) {
					nodes.removeLast();
				}
			}


		}
	}

	public float getXShift(Node node) {
		float shift = 0;
		for (Node g : nodes) {
			if (g == node) {
				return shift;
			} else {
				shift += g.getWidth();
			}
		}
		return 0;
	}

	public void completeCurrent() {
		if (!nodes.isEmpty() && nodes.getLast() instanceof AutoCompleteNode) {
			((AutoCompleteNode) nodes.getLast()).tryToComplete();
		}
	}

}
