package su.levenetc.androidplayground.queryline.drawers;

import su.levenetc.androidplayground.queryline.nodes.AutoCompleteNode;
import su.levenetc.androidplayground.queryline.nodes.Node;
import su.levenetc.androidplayground.queryline.nodes.SpaceNode;
import su.levenetc.androidplayground.queryline.nodes.StaticNode;

public interface DrawersFactory {
	AutoCompleteNode autoComplete();

	SpaceNode space();

	StaticNode staticNode();

	Node next();
}
