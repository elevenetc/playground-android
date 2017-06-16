package su.levenetc.androidplayground.mergeview;

/**
 * Created by eugene.levenetc on 16/06/2017.
 */
public class BracketRorZ implements Mergable {

	private String value;
	private boolean containsR;
	private boolean containsZ;

	public BracketRorZ(String value) {

		this.value = value;

		containsR = value.contains("{R}");
		containsZ = value.contains("{Z}");
	}

	public String getValue() {
		return value;
	}

	@Override public boolean mergableWith(Mergable value) {
		if (!(value instanceof BracketRorZ)) return false;
		return this.containsR && ((BracketRorZ) value).containsR
				||
				this.containsZ && ((BracketRorZ) value).containsZ;
	}
}