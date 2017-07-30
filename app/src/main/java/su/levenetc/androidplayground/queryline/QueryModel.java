package su.levenetc.androidplayground.queryline;

import su.levenetc.androidplayground.queryline.drawers.DrawersFactory;
import su.levenetc.androidplayground.queryline.nodes.AutoCompleteNode;
import su.levenetc.androidplayground.queryline.nodes.Node;
import su.levenetc.androidplayground.queryline.nodes.StaticNode;

import java.util.Deque;
import java.util.LinkedList;

public class QueryModel {

	Deque<Node> nodes = new LinkedList<>();
	private DrawersFactory nodesFactory;

	public QueryModel(DrawersFactory nodesFactory) {
		this.nodesFactory = nodesFactory;
	}

	public int size() {
		return nodes.size();
	}

	public void addNode(Node node) {
		nodes.add(node);
	}

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Node node : nodes) sb.append(node.toString());
		return sb.toString();
	}

	public Deque<Node> getNodes() {
		return nodes;
	}

	public float getXShift(Node node) {
		float shift = 0;
		for (Node g : nodes) {
			if (g == node) return shift;
			else shift += g.getWidth();
		}
		return 0;
	}

	public void convertToStatic(Node toConvert) {

		for (Node toRemove : nodes) {
			if (toConvert == toRemove) {
				nodes.remove(toRemove);
				final StaticNode staticNode = nodesFactory.staticNode();
				staticNode.setText(toRemove);
				nodes.addLast(staticNode);
				nodes.add(nodesFactory.space());
				nodes.add(nodesFactory.next());
				break;
			}
		}
	}

	public void append(char string) {

		if (nodes.isEmpty()) {
			final AutoCompleteNode chars = nodesFactory.autoComplete();
			nodes.add(chars);
		}

		nodes.getLast().append(string);

		if (nodes.getLast() instanceof AutoCompleteNode) {
			((AutoCompleteNode) nodes.getLast()).updateAutoComplete();
		}
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

	public void completeCurrent() {
		if (!nodes.isEmpty() && nodes.getLast() instanceof AutoCompleteNode) {
			((AutoCompleteNode) nodes.getLast()).tryToComplete();
		}
	}

}
