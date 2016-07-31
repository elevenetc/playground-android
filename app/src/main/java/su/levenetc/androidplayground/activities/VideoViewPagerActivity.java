package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.adapters.VideoPagerAdapter;
import su.levenetc.androidplayground.views.AnimatedTextView;
import su.levenetc.androidplayground.views.VideoViewPager;

/**
 * Created by Eugene Levenetc on 31/07/2016.
 */
public class VideoViewPagerActivity extends AppCompatActivity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_viewpager);

		final String[] texts = new String[]{
				"Text A",
				"Text B",
				"Text C"
		};

		final VideoViewPager videoViewPager = (VideoViewPager) findViewById(R.id.video_viewpager);
		final AnimatedTextView animatedTextView = (AnimatedTextView) findViewById(R.id.text_animated);
		final VideoPagerAdapter adapter = new VideoPagerAdapter(new int[]{
				R.raw.video_01,
				R.raw.video_02,
				R.raw.video_03
		});
		videoViewPager.setAdapter(adapter);


		videoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override public void onPageSelected(int position) {
				VideoView videoView = adapter.getItem(position);
				videoView.seekTo(0);
				videoView.start();
				animatedTextView.changeText(texts[position]);
			}

			@Override public void onPageScrollStateChanged(int state) {

			}
		});

		animatedTextView.changeText(texts[0]);
	}
}
