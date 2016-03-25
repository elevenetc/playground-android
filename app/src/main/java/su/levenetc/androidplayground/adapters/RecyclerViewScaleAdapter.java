package su.levenetc.androidplayground.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import su.levenetc.androidplayground.prototypes.timelineview.ScalableRecyclerView;
import su.levenetc.androidplayground.utils.DoubleSizeClickListener;
import su.levenetc.androidplayground.views.SineAnimatedView;

/**
 * Created by Eugene Levenetc on 26/03/2016.
 */
public class RecyclerViewScaleAdapter extends RecyclerView.Adapter<RecyclerViewScaleAdapter.VH> {

	private ScalableRecyclerView scalableRecyclerView;
	private List<ScaleModel> models;

	public RecyclerViewScaleAdapter(ScalableRecyclerView scalableRecyclerView, List<ScaleModel> models) {
		this.scalableRecyclerView = scalableRecyclerView;
		this.models = models;
	}

	@Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {

		SineAnimatedView view = new SineAnimatedView(parent.getContext());
		view.setOnClickListener(new DoubleSizeClickListener());
		view.setScaleValueY(scalableRecyclerView.getRecycleScaleY());

		return new VH(view);
	}

	@Override public int getItemCount() {
		return models.size();
	}

	@Override public void onBindViewHolder(VH holder, int position) {
		SineAnimatedView itemView = (SineAnimatedView) holder.itemView;
		itemView.setItemId(position);
		itemView.setScaleValueY(models.get(position).scale);
	}

	public static class VH extends RecyclerView.ViewHolder {

		public VH(View itemView) {
			super(itemView);
		}
	}

	public static class ScaleModel {
		public float scale = 1f;
	}
}
