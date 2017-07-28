package su.levenetc.androidplayground.models;

import android.graphics.Canvas;
import android.graphics.Rect;
import su.levenetc.androidplayground.utils.Paints;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class QueryModel {

	Deque<Group> groups = new LinkedList<>();
	Set<String> autocomplete = new HashSet<>();

	public QueryModel() {

	}

	public void addAutoComplete(String value) {
		autocomplete.add(value);
	}

	public void append(String ch) {

		if (groups.isEmpty()) {
			final AutoCompleteGroup chars = new AutoCompleteGroup(this, autocomplete);
			groups.add(chars);
		}

		groups.getLast().append(ch);

		if (groups.getLast() instanceof AutoCompleteGroup) {
			((AutoCompleteGroup) groups.getLast()).updateAutoComplete();
		}
	}

	private void convertToFixed(Group toConvert) {

		for (Group group : groups) {
			if (toConvert == group) {
				groups.remove(group);
				groups.addLast(new FixedGroup(this, group));
				groups.add(new AutoCompleteGroup(this, autocomplete));
				break;
			}
		}
	}

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Group group : groups) {
			sb.append(group.toString());
		}
		return sb.toString();
	}

	public void draw(Canvas canvas) {
		for (Group group : groups) group.draw(canvas);
	}

	public void deleteLast() {
		if (!groups.isEmpty()) {
			final Group last = groups.getLast();

			if (last.isEmpty()) {
				groups.removeLast();
			} else {
				if (last instanceof AutoCompleteGroup) {
					((AutoCompleteGroup) last).deleteLast();
					((AutoCompleteGroup) last).updateAutoComplete();
				} else if (last instanceof FixedGroup) {
					groups.removeLast();
				}
			}


		}
	}

	private float getXShift(Group group) {
		float shift = 0;
		for (Group g : groups) {
			if (g == group) {
				return shift;
			} else {
				shift += g.getWidth();
			}
		}
		return 0;
	}

	public void completeCurrent() {
		if (!groups.isEmpty() && groups.getLast() instanceof AutoCompleteGroup) {
			((AutoCompleteGroup) groups.getLast()).tryToComplete();
		}
	}

	static class AutoCompleteGroup extends Group {

		StringBuilder autoComplete = new StringBuilder();
		private Set<String> autocomplete;

		public AutoCompleteGroup(QueryModel queryModel, Set<String> autocomplete) {
			super(queryModel);
			this.autocomplete = autocomplete;
		}

		@Override void drawGroup(Canvas canvas) {


			final String str = text.toString();
			Paints.Font.Black_26.getTextBounds(str, 0, str.length(), textBounds);
			canvas.drawText(str, 0, textBounds.height(), Paints.Font.Black_26);

			if (autoComplete.length() > 0) {
				final CharSequence complete = autoComplete.subSequence(text.length(), autoComplete.length());
				canvas.drawText(String.valueOf(complete), textBounds.width(), textBounds.height(), Paints.Font.Black_26_Alpha_50);
			}
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

		private void updateAutoComplete() {
			//final AutoCompleteGroup auto = (AutoCompleteGroup) groups.getLast();
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
					queryModel.convertToFixed(this);
				}

			} else {
				setAuto(null);
			}
		}

		public void tryToComplete() {
			if (autoComplete.length() > 0) {
				set(autoComplete.toString());
				queryModel.convertToFixed(this);
			}
		}
	}

	static class FixedGroup extends Group {

		public FixedGroup(QueryModel queryModel, Group group) {
			super(queryModel);
			text.setLength(0);
			text.append(group.toString());
		}

		@Override void drawGroup(Canvas canvas) {
			final String str = text.toString();
			Paints.Font.Black_26.getTextBounds(str, 0, text.length(), textBounds);
			canvas.drawText(str, 0, textBounds.height(), Paints.Font.Black_26);
			canvas.drawRect(0, 0, textBounds.width(), textBounds.height(), Paints.Stroke.Red);
		}
	}

	abstract static class Group {

		protected StringBuilder text = new StringBuilder();
		protected Rect textBounds = new Rect();

		protected QueryModel queryModel;

		public Group(QueryModel queryModel) {
			this.queryModel = queryModel;
		}

		@Override public String toString() {
			return text.toString();
		}

		public void append(String value) {
			text.append(value);
		}

		public void set(Group group) {
			set(group.toString());
		}

		public void set(String value) {
			text.setLength(0);
			text.append(value);
		}

		void draw(Canvas canvas) {
			canvas.save();
			canvas.translate(queryModel.getXShift(this), 0);

			drawGroup(canvas);

			canvas.restore();
		}

		abstract void drawGroup(Canvas canvas);

		public boolean isEmpty() {
			return text.length() == 0;
		}

		public float getWidth() {
			return textBounds.width();
		}
	}
}
