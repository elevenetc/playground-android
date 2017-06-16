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

	public List<Pair> getPairs() {
		List<Pair> result = new LinkedList<>();
		for (int l = 0; l < left.size(); l++) {
			Mergable leftValue = left.get(l);
			for (int r = 0; r < right.size(); r++) {
				Mergable rightValue = right.get(r);
				if (leftValue.mergableWith(rightValue))
					result.add(new Pair(leftValue, rightValue, l, r));
			}
		}
		return result;
	}
}