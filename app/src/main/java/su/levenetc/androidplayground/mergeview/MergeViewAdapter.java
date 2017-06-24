package su.levenetc.androidplayground.mergeview;

import android.support.v7.widget.RecyclerView;
import su.levenetc.androidplayground.adapters.MergeListAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 21/06/2017.
 */
public class MergeViewAdapter {

	private MergeListAdapter leftAdapter;
	private MergeListAdapter rightAdapter;
	private RecyclerView leftList;
	private RecyclerView rightList;
	private MergeViewSeparator separator;
	private List<Mergable> leftData;
	private List<Mergable> rightData;

	public MergeViewAdapter(List<Mergable> leftData, List<Mergable> rightData, ViewItemFactory viewFactory) {
		this.leftData = leftData;
		this.rightData = rightData;
		leftAdapter = new MergeListAdapter(leftData, viewFactory);
		rightAdapter = new MergeListAdapter(rightData, viewFactory);


	}

	private void delete(Mergable data) {
		int position = leftAdapter.getPosition(data);
		leftAdapter.delete(data);
		leftAdapter.notifyItemRemoved(position);
		separator.replace(position, data);
	}

	private void remove(Mergable data) {

	}

	public void setViews(RecyclerView leftList, RecyclerView rightList, MergeViewSeparator separator) {
		this.separator = separator;
		this.leftList = leftList;
		this.rightList = rightList;

		initLists();
		initSeparator();
	}

	private void initLists() {

		this.leftList.setAdapter(leftAdapter);
		this.rightList.setAdapter(rightAdapter);

		leftAdapter.setClickHandler(new MergeListAdapter.ClickHandler() {
			@Override public void onClicked(Mergable data) {
				delete(data);
			}
		});

		rightAdapter.setClickHandler(new MergeListAdapter.ClickHandler() {
			@Override public void onClicked(Mergable data) {

			}
		});
	}

	private void initSeparator() {
		separator.buildPairs(buildPairs(leftData, rightData));
		separator.setLists(leftList, rightList);
	}

	public List<Pair> buildPairs(List<Mergable> left, List<Mergable> right) {
		List<Pair> result = new LinkedList<>();
		for (int l = 0; l < left.size(); l++) {
			Mergable leftValue = left.get(l);
			for (int r = 0; r < right.size(); r++) {
				Mergable rightValue = right.get(r);
				if (leftValue.mergableWith(rightValue)) {
					result.add(new Pair(leftValue, rightValue, l, r));
				}

			}
		}
		return result;
	}
}
