package su.levenetc.androidplayground.mergeview;

/**
 * Created by eugene.levenetc on 16/06/2017.
 */
public class Pair {

	public final Mergable leftValue;
	public final Mergable rightValue;
	public int leftIndex;
	public int rightIndex;

	public Pair(
			Mergable leftValue, Mergable rightValue,
			int leftIndex, int rightIndex
	) {

		this.leftValue = leftValue;
		this.rightValue = rightValue;
		this.leftIndex = leftIndex;
		this.rightIndex = rightIndex;
	}
}
