package su.levenetc.androidplayground.mergeview;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 16/06/2017.
 */
public class PairBuilder {

	private final List<Mergable> left;
	private final List<Mergable> right;

	public PairBuilder(List<Mergable> left, List<Mergable> right) {

		this.left = left;
		this.right = right;
	}


}