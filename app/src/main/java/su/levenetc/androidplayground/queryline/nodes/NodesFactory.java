package su.levenetc.androidplayground.queryline.nodes;

import su.levenetc.androidplayground.queryline.QueryModel;

public abstract class NodesFactory {

	protected QueryModel queryModel;

	public abstract Node next();

	public void setQueryModel(QueryModel queryModel) {

		this.queryModel = queryModel;
	}
}
