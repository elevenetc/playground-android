package su.levenetc.androidplayground.adapters;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import su.levenetc.androidplayground.views.TextureVideoView;

/**
 * Created by Eugene Levenetc on 31/07/2016.
 */
public class VideoPagerAdapter extends PagerAdapter {

	private int[] rawFilesIds;
	private TextureVideoView[] items;

	public VideoPagerAdapter(int[] rawFilesIds) {
		this.rawFilesIds = rawFilesIds;
		items = new TextureVideoView[rawFilesIds.length];
	}

	@Override public Object instantiateItem(ViewGroup container, int position) {
		//VideoView videoView = new VideoView(container.getContext());
		TextureVideoView videoView = new TextureVideoView(container.getContext());
		Uri uri = Uri.parse("android.resource://su.levenetc.androidplayground/raw/" + rawFilesIds[position]);
		//videoView.setBackgroundColor(position % 2 == 0 ? Color.RED : Color.BLUE);
		videoView.setVideoURI(uri);
		//videoView.setZOrderOnTop(true);
		videoView.seekTo(0);
		videoView.start();
		videoView.stopPlayback();
		//videoView.start();
		container.addView(videoView);
		items[position] = videoView;
		return videoView;
	}

	@Override public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
		TextureVideoView videoView = (TextureVideoView) object;
		videoView.stopPlayback();
		videoView.suspend();
		items[position] = null;
	}

	@Override public int getCount() {
		return rawFilesIds.length;
	}

	@Override public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	public TextureVideoView getItem(int position) {
		return items[position];
	}

	public void resetTime() {
		for (TextureVideoView item : items)
			if (item != null) item.seekTo(0);
	}
}
