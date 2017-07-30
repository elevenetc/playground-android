package su.levenetc.androidplayground.queryline.nodes;

import java.util.Set;

public class AutoCompleteNode extends Node {

	public StringBuilder autoCompleteText = new StringBuilder();
	private Set<String> variants;

	public AutoCompleteNode(Set<String> variants) {
		this.variants = variants;
	}

	public void setAuto(String value) {
		if (value == null) {
			autoCompleteText.setLength(0);
		} else {
			autoCompleteText.setLength(0);
			autoCompleteText.append(value);
		}

	}

	public boolean isFilled() {
		return autoCompleteText.length() == currentText.length();
	}

	public void deleteLast() {
		currentText.setLength(currentText.length() - 1);
	}

	public void updateAutoComplete() {
		final String toComplete = toString();
		if (toComplete.isEmpty()) return;

		int minLength = Integer.MAX_VALUE;
		final char[] charsToComplete = toComplete.toCharArray();
		String v = null;

		for (String value : variants) {

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
				queryModel.handleFilledNode();
			}

		} else {
			setAuto(null);
		}
	}

	public void tryToComplete() {
		if (autoCompleteText.length() > 0) {
			setText(autoCompleteText.toString());
			queryModel.handleFilledNode();
		}
	}
}
