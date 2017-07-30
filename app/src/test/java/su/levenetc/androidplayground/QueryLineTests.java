package su.levenetc.androidplayground;

import android.support.annotation.NonNull;
import org.junit.Test;
import su.levenetc.androidplayground.queryline.QueryModel;
import su.levenetc.androidplayground.queryline.nodes.AutoCompleteNode;
import su.levenetc.androidplayground.queryline.nodes.Node;
import su.levenetc.androidplayground.queryline.nodes.NodesFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QueryLineTests {

	@Test
	public void testAutoComplete() {
		//select name,age from user
		final QueryModel queryModel = getQueryModel();

		queryModel.append('h');
		queryModel.tryToComplete();
		queryModel.append('s');
		queryModel.append('a');
		queryModel.tryToComplete();


		final LinkedList<Node> nodes = queryModel.getNodes();
		assertEquals(3, nodes.size());
		assertEquals("hello", nodes.get(0).toString());
		assertEquals("sample", nodes.get(1).toString());
		assertEquals("", nodes.get(2).toString());
	}

	@Test
	void list() {
		new BuildNode().addSingleVariant(
				new BuildNode("select-last"),
				"select-a", "select-b"
		);
	}

	@NonNull private QueryModel getQueryModel() {
		return new QueryModel(new NodesFactory() {

			@Override public Node next() {
				final AutoCompleteNode result = new AutoCompleteNode(new HashSet<String>() {{
					add("hello");
					add("sample");
				}});
				result.setQueryModel(queryModel);
				return result;
			}
		});
	}

	static class BuildNode {

		Map<String, BuildNode> variants = new HashMap<>();

		public BuildNode(String... endVariants) {

		}

		void addVariant(String variant, BuildNode node) {
			variants.put(variant, node);
		}

		void addSingleVariant(BuildNode node, String... variants) {
			for (String variant : variants) {
				this.variants.put(variant, node);
			}
		}
	}

}
