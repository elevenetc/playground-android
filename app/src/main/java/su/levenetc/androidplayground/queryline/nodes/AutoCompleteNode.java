package su.levenetc.androidplayground.queryline.nodes;

import java.util.Set;

public class AutoCompleteNode extends Node {

	public StringBuilder autoComplete = new StringBuilder();
	private Set<String> autocomplete;

	public AutoCompleteNode(Set<String> autocomplete) {
		this.autocomplete = autocomplete;
	}

	public void setAuto(String value) {
		if (value == null) {
			autoComplete.setLength(0);
		} else {
			autoComplete.setLength(0);
			autoComplete.append(value);
		}

	}

	public boolean isFilled() {
		return autoComplete.length() == text.length();
	}

	public void deleteLast() {
		text.setLength(text.length() - 1);
	}

	public void updateAutoComplete() {
		final String toComplete = toString();
		if (toComplete.isEmpty()) return;

		int minLength = Integer.MAX_VALUE;
		final char[] charsToComplete = toComplete.toCharArray();
		String v = null;

		for (String value : autocomplete) {

			if (charsToComplete.length > value.length()) continue;
			int length = 0;

			for (int i = 0; i < charsToComplete.length; i++) {
				final char ch = charsToComplete[i];
				if (ch == value.charAt(i)) {
					length++;
				} else {
					length = 0;
					break;
				}
			}

			if (length > 0 && length < minLength) {
				v = value;
			}
		}

		if (v != null) {
			setAuto(v);

			if (isFilled()) {
				queryModel.convertToStatic(this);
			}

		} else {
			setAuto(null);
		}
	}

	public void tryToComplete() {
		if (autoComplete.length() > 0) {
			setText(autoComplete.toString());
			queryModel.convertToStatic(this);
		}
	}
}
