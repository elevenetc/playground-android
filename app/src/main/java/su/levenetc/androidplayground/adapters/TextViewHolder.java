package su.levenetc.androidplayground.adapters;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

/**
 * Created by eugene.levenetc on 15/06/2017.
 */
public class TextViewHolder extends RecyclerView.ViewHolder {

	public TextViewHolder(TextView itemView) {
		super(itemView);
	}

	public void setValue(String value) {
		((TextView) itemView).setText(value);
	}
}
