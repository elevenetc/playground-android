package su.levenetc.androidplayground.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.mergeview.BracketR;
import su.levenetc.androidplayground.mergeview.Mergable;
import su.levenetc.androidplayground.mergeview.MergableItemView;
import su.levenetc.androidplayground.mergeview.MergeView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 15/06/2017.
 */
public class MergeViewActivity extends AppCompatActivity {

	@BindView(R.id.merge_view) MergeView mergeView;

	View activeList;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_merge_view);
		ButterKnife.bind(this);


		List<Mergable> leftData = getLeftData(50);
		List<Mergable> rightData = getRightData(100);
		final Context context = this;

		mergeView.setData(leftData, rightData, () -> new TV(context));
	}

	static class TV extends TextView implements MergableItemView<BracketR> {

		public TV(Context context) {
			super(context);
		}

		public TV(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override public void set(BracketR data) {
			setText(data.getValue());
		}
	}

	@NonNull private List<Mergable> getLeftData(int length) {
		List<Mergable> result = new LinkedList<>();
		for (int i = 0; i < length; i++) {

			if (i == 3) {
				result.add(new BracketR("hello{R}"));
			} else {
				result.add(new BracketR("Ssss"));
			}

		}
		return result;
	}

	@NonNull private List<Mergable> getRightData(int length) {
		List<Mergable> result = new LinkedList<>();
		for (int i = 0; i < length; i++) {

			if (i == 10) {
				result.add(new BracketR("hello{R}"));
			} else {
				result.add(new BracketR("Zzzz..."));
			}

		}
		return result;
	}

}
