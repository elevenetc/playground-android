package su.levenetc.androidplayground.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import su.levenetc.androidplayground.mergeview.Mergable;
import su.levenetc.androidplayground.mergeview.MergableItemView;
import su.levenetc.androidplayground.mergeview.ViewItemFactory;
import su.levenetc.androidplayground.utils.ViewUtils;

import java.util.List;

/**
 * Created by eugene.levenetc on 15/06/2017.
 */
public class MergeListAdapter extends RecyclerView.Adapter<MergeListAdapter.Holder> {

	private List<Mergable> data;
	private ViewItemFactory viewFactory;
	private ClickHandler clickHandler;

	public MergeListAdapter(List<Mergable> data, ViewItemFactory viewFactory) {

		this.data = data;
		this.viewFactory = viewFactory;
	}

	public void setClickHandler(ClickHandler clickHandler) {

		this.clickHandler = clickHandler;
	}

	public Mergable getItem(int position) {
		return data.get(position);
	}

	@Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
		final MergableItemView mView = viewFactory.create();
		final View view = (View) mView;
		view.setLayoutParams(ViewUtils.viewGroupMW());
		view.setOnClickListener(v -> clickHandler.onClicked(((MergableItemView) v).get()));
		return new Holder(view);
	}

	@Override public void onBindViewHolder(Holder holder, int position) {
		final MergableItemView view = (MergableItemView) holder.itemView;
		((View) view).setBackgroundColor(0);
		view.set(data.get(position));
	}

	@Override public int getItemCount() {
		return data.size();
	}

	public void delete(Mergable data) {
		this.data.remove(data);
	}

	public void add(Mergable data) {
		this.data.add(data);
	}

	public int getPosition(Mergable data) {
		List<Mergable> data1 = this.data;
		for (int i = 0; i < data1.size(); i++) {
			Mergable mergable = data1.get(i);
			if (data == mergable) {
				return i;
			}
		}
		return -1;
	}

	static class Holder extends RecyclerView.ViewHolder {

		public Holder(View itemView) {
			super(itemView);
		}
	}

	public interface ClickHandler<T extends Mergable> {
		void onClicked(T data);
	}
}
