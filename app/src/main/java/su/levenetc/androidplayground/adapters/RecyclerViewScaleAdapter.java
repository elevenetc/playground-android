package su.levenetc.androidplayground.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import su.levenetc.androidplayground.models.Timeline;
import su.levenetc.androidplayground.models.TimeSession;
import su.levenetc.androidplayground.utils.DoubleSizeClickListener;
import su.levenetc.androidplayground.views.TimeLineItemView;

/**
 * Created by Eugene Levenetc on 26/03/2016.
 */
public class RecyclerViewScaleAdapter extends RecyclerView.Adapter<RecyclerViewScaleAdapter.VH> {

	private TimeSession session;
	private float scaleY = 1f;

	public RecyclerViewScaleAdapter(TimeSession session) {
		this.session = session;
	}

	@Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {

		TimeLineItemView view = new TimeLineItemView(parent.getContext());
		view.setOnClickListener(new DoubleSizeClickListener());
		view.setScaleValueY(scaleY);

		return new VH(view);
	}

	public void updateScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	@Override public int getItemCount() {
		return session.size();
	}

	@Override public void onBindViewHolder(VH holder, int position) {
		TimeLineItemView itemView = (TimeLineItemView) holder.itemView;
		itemView.setItemId(position);
		Timeline timeline = session.getTimeLine(position);
		itemView.setScaleValueY(scaleY);
		itemView.setModels(session, timeline);
	}

	public static class VH extends RecyclerView.ViewHolder {

		public VH(View itemView) {
			super(itemView);
		}
	}

//	public static class ItemModel {
////		public float scale = 1f;
//		Timeline timeLine;
//
//		public ItemModel(Timeline timeLine) {
//			this.timeLine = timeLine;
//		}
//	}
}
