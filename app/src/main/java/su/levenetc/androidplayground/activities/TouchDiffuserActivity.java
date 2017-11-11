package su.levenetc.androidplayground.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.touchdiffuser.TouchDiffuser;
import su.levenetc.androidplayground.utils.ViewUtils;

public class TouchDiffuserActivity extends AppCompatActivity {

	private ViewGroup rootView;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_events);
		ViewGroup rootContainer = (ViewGroup) findViewById(R.id.root_container);
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(ViewUtils.getFilledRecyclerViewAdapter());

		viewPager.setAdapter(ViewUtils.getFilledViewPagerAdapter(this, 20));

		rootView = (ViewGroup) findViewById(android.R.id.content);

		Dialog dialog = new Dialog(this);
		dialog.setTitle("hello");
		dialog.show();

	}

//	@Override public boolean dispatchTouchEvent(MotionEvent event) {
//		View v = getCurrentFocus();
//		boolean ret = super.dispatchTouchEvent(event);
//		return true;
//	}

//	@Override public boolean onTouchEvent(MotionEvent event) {
//		return TouchDiffuser.inst.onTouchEvent(rootView, event);
//	}
}