package su.levenetc.androidplayground.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import su.levenetc.androidplayground.models.TimeLine;
import su.levenetc.androidplayground.models.TimeLineSession;
import su.levenetc.androidplayground.prototypes.timelineview.ScalableRecyclerView;
import su.levenetc.androidplayground.utils.DoubleSizeClickListener;
import su.levenetc.androidplayground.views.TimeLineItemView;

/**
 * Created by Eugene Levenetc on 26/03/2016.
 */
public class RecyclerViewScaleAdapter extends RecyclerView.Adapter<RecyclerViewScaleAdapter.VH> {

	private ScalableRecyclerView scalableRecyclerView;
	private TimeLineSession session;

	public RecyclerViewScaleAdapter(ScalableRecyclerView scalableRecyclerView, TimeLineSession session) {
		this.scalableRecyclerView = scalableRecyclerView;
		this.session = session;
	}

	@Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {

		TimeLineItemView view = new TimeLineItemView(parent.getContext());
		view.setOnClickListener(new DoubleSizeClickListener());
		view.setScaleValueY(scalableRecyclerView.getRecycleScaleY());

		return new VH(view);
	}

	@Override public int getItemCount() {
		return session.size();
	}

	@Override public void onBindViewHolder(VH holder, int position) {
		TimeLineItemView itemView = (TimeLineItemView) holder.itemView;
		itemView.setItemId(position);
		ItemModel model = session.getTimeLine(position);
		itemView.setScaleValueY(model.scale);
		itemView.setTimeLineItem(model.timeLine);
	}

	public static class VH extends RecyclerView.ViewHolder {

		public VH(View itemView) {
			super(itemView);
		}
	}

	public static class ItemModel {
		public float scale = 1f;
		TimeLine timeLine;

		public ItemModel(TimeLine timeLine) {
			this.timeLine = timeLine;
		}
	}
}
