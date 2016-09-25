package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.adapters.VideoPagerAdapter;
import su.levenetc.androidplayground.views.AnimatedTextView;
import su.levenetc.androidplayground.views.TextureVideoView;
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
//				R.raw.video_01,
//				R.raw.video_02,
//				R.raw.video_03
		});
		videoViewPager.setAdapter(adapter);


		videoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override public void onPageSelected(int position) {
				TextureVideoView videoView = adapter.getItem(position);
				adapter.resetTime();
				//videoView.seekTo(0);
				videoView.start();
				animatedTextView.changeText(texts[position]);

			}

			@Override public void onPageScrollStateChanged(int state) {

			}
		});

		ViewPager.PageTransformer pageTransformer = (page, position) -> {
			if (position <= -1.0f || position >= 1.0f) {
				page.setAlpha(0.0f);
			} else if (position == 0.0f) {
				page.setAlpha(1.0f);
			} else {
				// position is between -1.0F & 0.0F OR 0.0F & 1.0F
				page.setAlpha(1.0f - Math.abs(position));
			}
		};

		videoViewPager.setPageTransformer(false, pageTransformer);

		animatedTextView.changeText(texts[0]);
	}
}
