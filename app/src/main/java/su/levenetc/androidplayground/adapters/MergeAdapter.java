package su.levenetc.androidplayground.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import su.levenetc.androidplayground.mergeview.Mergable;
import su.levenetc.androidplayground.mergeview.MergableItemView;
import su.levenetc.androidplayground.mergeview.ViewItemFactory;

import java.util.List;

/**
 * Created by eugene.levenetc on 15/06/2017.
 */
public class MergeAdapter extends RecyclerView.Adapter<MergeAdapter.Holder> {

	private List<Mergable> data;
	private ViewItemFactory viewFactory;

	public MergeAdapter(List<Mergable> data, ViewItemFactory viewFactory) {

		this.data = data;
		this.viewFactory = viewFactory;
	}

	public Mergable getItem(int position) {
		return data.get(position);
	}

	@Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
		final MergableItemView view = viewFactory.create();
		((View) view).setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		return new Holder((View) view);
	}

	@Override public void onBindViewHolder(Holder holder, int position) {
		final MergableItemView view = (MergableItemView) holder.itemView;
		((View)view).setBackgroundColor(0);
		view.set(data.get(position));
	}

	@Override public int getItemCount() {
		return data.size();
	}

	static class Holder extends RecyclerView.ViewHolder {

		public Holder(View itemView) {
			super(itemView);
		}
	}
}
