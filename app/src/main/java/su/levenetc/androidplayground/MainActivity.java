package su.levenetc.androidplayground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.InjectUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import su.levenetc.androidplayground.adapters.RecyclerViewScaleAdapter;
import su.levenetc.androidplayground.models.TimeSession;
import su.levenetc.androidplayground.prototypes.timelineview.ScalableRecyclerView;
import su.levenetc.androidplayground.utils.SessionBus;
import su.levenetc.androidplayground.views.SampleImagesRecycler;

public class MainActivity extends AppCompatActivity {

	private TimeSession session;
	private RecyclerViewScaleAdapter adapter;
	private ScalableRecyclerView recyclerView;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SampleImagesRecycler recycler = (SampleImagesRecycler) findViewById(R.id.recycler_images);
		recyclerView = (ScalableRecyclerView) findViewById(R.id.list);

		session = new TimeSession();

//		Timeline timeLineA = Timeline.Builder.create(0)
//				.addPeriod(100, Color.RED)
//				.addPeriod(100, Color.GREEN).build();
//
//		final Timeline timeLineB = Timeline.Builder.create(100)
//				.addPeriod(100, Color.RED)
//				.addPeriod(100, Color.GREEN).build();
//
//		Timeline timeLineC = Timeline.Builder.create(200)
//				.addPeriod(100, Color.RED)
//				.addPeriod(400, Color.YELLOW)
//				.addPeriod(50, Color.GREEN).build();
//
//		session.add(timeLineA, timeLineB, timeLineC);

//		models.add(new RecyclerViewScaleAdapter.ItemModel(timeLineA));
//		models.add(new RecyclerViewScaleAdapter.ItemModel(timeLineB));
//		models.add(new RecyclerViewScaleAdapter.ItemModel(timeLineC));

		adapter = new RecyclerViewScaleAdapter(session);
		recyclerView.setAdapter(adapter);

//		recyclerView.postDelayed(new Runnable() {
//			@Override public void run() {
//				session.startPeriod(timeLineB, new TimePeriod(800, Color.CYAN));
////				timeLineB.add();
//				adapter.notifyDataSetChanged();
//			}
//		}, 1500);

//		addItem();

//		Picasso picasso = Picasso.with(this);
		Picasso.Builder builder = new Picasso.Builder(this);
		Picasso picasso = builder.executor(Executors.newSingleThreadExecutor()).build();
//		picasso.load(new File())
		InjectUtils.injectPicasso(picasso, session);
		recycler.setPicasso(picasso);

		session.setBus(new SessionBus(adapter));
	}

//	private void addItem() {
//		recyclerView.postDelayed(new Runnable() {
//			@Override public void run() {
//				Timeline timeLine = Timeline.Builder.create(300).addPeriod(200, Color.BLUE).build();
//				session.add(timeLine);
//				adapter.notifyDataSetChanged();
//			}
//		}, 1000);
//	}
}