package su.levenetc.androidplayground.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by eugene.levenetc on 15/06/2017.
 */
public class TextLinesAdapter extends RecyclerView.Adapter<TextViewHolder> {

	private String[] textLines;

	public TextLinesAdapter(String... textLines) {

		this.textLines = textLines;
	}

	public int getIndex(String value) {
		for (int i = 0; i < textLines.length; i++) {
			if (value.equals(textLines[i])) return i;
		}
		return -1;
	}

	public String getItem(int position) {
		return textLines[position];
	}

	@Override public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new TextViewHolder(new TextView(parent.getContext()));
	}

	@Override public void onBindViewHolder(TextViewHolder holder, int position) {
		holder.setValue(textLines[position]);
	}

	@Override public int getItemCount() {
		return textLines.length;
	}
}
