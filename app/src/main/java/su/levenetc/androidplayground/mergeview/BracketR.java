package su.levenetc.androidplayground.mergeview;

/**
 * Created by eugene.levenetc on 16/06/2017.
 */
public class BracketR implements Mergable {

	private String value;
	private boolean containsBracketR;

	public BracketR(String value) {

		this.value = value;
		containsBracketR = value.contains("{R}");
	}

	public String getValue() {
		return value;
	}

	@Override public boolean mergableWith(Mergable value) {
		if (!(value instanceof BracketR)) return false;
		return this.containsBracketR && ((BracketR) value).containsBracketR;
	}
}
