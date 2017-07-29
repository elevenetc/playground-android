package su.levenetc.androidplayground.queryline.nodes;

import java.util.HashSet;

public class SQLiteInitNode extends AutoCompleteNode {
	public SQLiteInitNode() {
		super(new HashSet<String>() {{
			add("select");
			add("create");
		}});
	}
}
