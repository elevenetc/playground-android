package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.utils.ViewUtils;

public class TouchEventsActivity extends AppCompatActivity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_events);
		ViewGroup rootContainer = (ViewGroup) findViewById(R.id.root_container);
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(ViewUtils.getFilledRecyclerViewAdapter());

		viewPager.setAdapter(ViewUtils.getFilledViewPagerAdapter(this, 20));
	}
}