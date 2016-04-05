package su.levenetc.androidplayground.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Eugene Levenetc on 01/04/2016.
 */
public class SampleImagesRecycler extends RecyclerView {

	private Picasso picasso;

	public SampleImagesRecycler(Context context) {
		super(context);
		config(context);
	}

	public SampleImagesRecycler(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(context);
	}

	private void config(Context context) {
		setLayoutManager(new LinearLayoutManager(context));
	}

	public void setPicasso(Picasso picasso) {
		this.picasso = picasso;
		setAdapter(new Adapter());
	}

	public class Adapter extends RecyclerView.Adapter<VH> {

		private SparseArray<String> images = new SparseArray<String>() {{

			put(0, "/images/img_001.png");
			put(1, "/images/img_002.png");
			put(2, "/images/img_003.png");
			put(3, "/images/img_004.png");
//			put(4, "https://dl.dropboxusercontent.com/u/4125923/images/img_005.png");
//			put(5, "https://dl.dropboxusercontent.com/u/4125923/images/img_006.png");
//			put(6, "https://dl.dropboxusercontent.com/u/4125923/images/img_007.png");
//			put(7, "https://dl.dropboxusercontent.com/u/4125923/images/img_008.png");
//			put(8, "https://dl.dropboxusercontent.com/u/4125923/images/img_009.png");
//			put(9, "https://dl.dropboxusercontent.com/u/4125923/images/img_010.png");
//			put(10, "https://dl.dropboxusercontent.com/u/4125923/images/img_011.png");
//			put(11, "https://dl.dropboxusercontent.com/u/4125923/images/img_012.png");
//			put(12, "https://dl.dropboxusercontent.com/u/4125923/images/img_013.png");
//			put(13, "https://dl.dropboxusercontent.com/u/4125923/images/img_014.png");
//			put(14, "https://dl.dropboxusercontent.com/u/4125923/images/img_015.png");
//			put(15, "https://dl.dropboxusercontent.com/u/4125923/images/img_016.png");
//			put(16, "https://dl.dropboxusercontent.com/u/4125923/images/img_017.png");
//			put(17, "https://dl.dropboxusercontent.com/u/4125923/images/img_018.png");
//			put(18, "https://dl.dropboxusercontent.com/u/4125923/images/img_019.png");
//			put(19, "https://dl.dropboxusercontent.com/u/4125923/images/img_020.png");

//			put(0, "https://dl.dropboxusercontent.com/u/4125923/images/img_001.png");
//			put(1, "https://dl.dropboxusercontent.com/u/4125923/images/img_002.png");
//			put(2, "https://dl.dropboxusercontent.com/u/4125923/images/img_003.png");
//			put(3, "https://dl.dropboxusercontent.com/u/4125923/images/img_004.png");
//			put(4, "https://dl.dropboxusercontent.com/u/4125923/images/img_005.png");
//			put(5, "https://dl.dropboxusercontent.com/u/4125923/images/img_006.png");
//			put(6, "https://dl.dropboxusercontent.com/u/4125923/images/img_007.png");
//			put(7, "https://dl.dropboxusercontent.com/u/4125923/images/img_008.png");
//			put(8, "https://dl.dropboxusercontent.com/u/4125923/images/img_009.png");
//			put(9, "https://dl.dropboxusercontent.com/u/4125923/images/img_010.png");
//			put(10, "https://dl.dropboxusercontent.com/u/4125923/images/img_011.png");
//			put(11, "https://dl.dropboxusercontent.com/u/4125923/images/img_012.png");
//			put(12, "https://dl.dropboxusercontent.com/u/4125923/images/img_013.png");
//			put(13, "https://dl.dropboxusercontent.com/u/4125923/images/img_014.png");
//			put(14, "https://dl.dropboxusercontent.com/u/4125923/images/img_015.png");
//			put(15, "https://dl.dropboxusercontent.com/u/4125923/images/img_016.png");
//			put(16, "https://dl.dropboxusercontent.com/u/4125923/images/img_017.png");
//			put(17, "https://dl.dropboxusercontent.com/u/4125923/images/img_018.png");
//			put(18, "https://dl.dropboxusercontent.com/u/4125923/images/img_019.png");
//			put(19, "https://dl.dropboxusercontent.com/u/4125923/images/img_020.png");
		}};

		@Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
			ImageView imageView = new ImageView(parent.getContext());
			imageView.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 100
			));
			return new VH(imageView);
		}

		@Override public void onBindViewHolder(VH holder, int position) {
			picasso
					.load(images.get(position))
					.into(((ImageView) holder.itemView));
		}

		@Override public int getItemCount() {
//			return 1;
//			return 2;
			return images.size();
		}
	}

	public static class VH extends RecyclerView.ViewHolder {

		public VH(View itemView) {
			super(itemView);
		}
	}
}
