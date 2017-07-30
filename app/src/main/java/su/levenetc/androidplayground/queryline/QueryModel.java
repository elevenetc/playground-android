package su.levenetc.androidplayground.queryline;

import su.levenetc.androidplayground.queryline.nodes.AutoCompleteNode;
import su.levenetc.androidplayground.queryline.nodes.Node;
import su.levenetc.androidplayground.queryline.nodes.NodesFactory;
import su.levenetc.androidplayground.queryline.nodes.StaticNode;

import java.util.LinkedList;

public class QueryModel {

	LinkedList<Node> nodes = new LinkedList<>();
	private NodesFactory nodesFactory;

	public QueryModel(NodesFactory nodesFactory) {
		this.nodesFactory = nodesFactory;
		nodesFactory.setQueryModel(this);
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

	public LinkedList<Node> getNodes() {
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

	public void handleFilledNode() {
		nodes.add(nodesFactory.next());
	}

	public void append(char ch) {

		if (nodes.isEmpty()) {
			final Node node = nodesFactory.next();
			nodes.add(node);
		}

		nodes.getLast().append(ch);

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

	public void tryToComplete() {
		if (!nodes.isEmpty() && nodes.getLast() instanceof AutoCompleteNode) {
			((AutoCompleteNode) nodes.getLast()).tryToComplete();
		}
	}

}
