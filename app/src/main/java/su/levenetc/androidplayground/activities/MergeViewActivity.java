package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.adapters.TextLinesAdapter;
import su.levenetc.androidplayground.views.MergeView;

/**
 * Created by eugene.levenetc on 15/06/2017.
 */
public class MergeViewActivity extends AppCompatActivity {

	@BindView(R.id.list_left) RecyclerView leftList;
	@BindView(R.id.list_right) RecyclerView rightList;
	@BindView(R.id.merge_view) MergeView mergeView;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_merge_view);
		ButterKnife.bind(this);
		leftList.setLayoutManager(new LinearLayoutManager(this));
		rightList.setLayoutManager(new LinearLayoutManager(this));


		leftList.setOnScrollChangeListener(new View.OnScrollChangeListener() {
			@Override public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
				mergeView.invalidate();
			}
		});

		rightList.setOnScrollChangeListener(new View.OnScrollChangeListener() {
			@Override public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
				mergeView.invalidate();
			}
		});

		String[] leftData = getLeftData();
		String[] rightData = getRightData();

		leftList.setAdapter(new TextLinesAdapter(leftData));
		rightList.setAdapter(new TextLinesAdapter(rightData));

		mergeView.setLists(leftList, rightList);
	}

	@NonNull private String[] getLeftData() {
		String[] leftData = new String[100];
		for (int i = 0; i < leftData.length; i++) {

			if (i == 3) {
				leftData[i] = "bbb";
			} else {
				leftData[i] = "Zzz";
			}

		}
		return leftData;
	}

	@NonNull private String[] getRightData() {
		String[] leftData = new String[100];
		for (int i = 0; i < leftData.length; i++) {

			if (i == 10) {
				leftData[i] = "bbb";
			} else {
				leftData[i] = "Fff";
			}

		}
		return leftData;
	}

}
