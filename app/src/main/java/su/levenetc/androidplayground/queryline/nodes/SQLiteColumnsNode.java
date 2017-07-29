package su.levenetc.androidplayground.queryline.nodes;

import java.util.HashSet;

public class SQLiteColumnsNode extends AutoCompleteNode {
	public SQLiteColumnsNode() {
		super(new HashSet<String>() {{
			add("name");
			add("age");
		}});
	}
}
