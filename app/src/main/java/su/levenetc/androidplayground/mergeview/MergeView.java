package su.levenetc.androidplayground.mergeview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import su.levenetc.androidplayground.adapters.MergeAdapter;
import su.levenetc.androidplayground.utils.ViewUtils;

import java.util.LinkedList;
import java.util.List;

import static su.levenetc.androidplayground.utils.ViewUtils.linearWM;

/**
 * Created by eugene.levenetc on 16/06/2017.
 */
public class MergeView extends LinearLayout {

	private RecyclerView leftList;
	private RecyclerView rightList;
	private MergeViewSeparator separator;
	private View activeList;
	private MergeAdapter leftAdapter;
	private MergeAdapter rightAdapter;

	public MergeView(Context context) {
		super(context);
		init();
	}

	public MergeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setOrientation(HORIZONTAL);

		leftList = new RecyclerView(getContext());
		rightList = new RecyclerView(getContext());
		separator = new MergeViewSeparator(getContext());

		leftList.setLayoutManager(new LinearLayoutManager(getContext()));
		rightList.setLayoutManager(new LinearLayoutManager(getContext()));

		initScroll();

		addView(leftList, linearWM(1));
		addView(separator, new LayoutParams((int) ViewUtils.dpToPx(50, getContext()), ViewGroup.LayoutParams.MATCH_PARENT));
		addView(rightList, linearWM(1));
	}

	private void initScroll() {


		leftList.setOnScrollChangeListener(new View.OnScrollChangeListener() {
			@Override public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
				separator.invalidate();
			}
		});

		rightList.setOnScrollChangeListener(new View.OnScrollChangeListener() {
			@Override public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
				separator.invalidate();
			}
		});

		leftList.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if (activeList == leftList)
					rightList.scrollBy(0, dy);
			}
		});

		rightList.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if (activeList == rightList)
					leftList.scrollBy(0, dy);
			}
		});

		rightList.setOnTouchListener((v, event) -> {

			if (activeList != null && activeList != rightList) return true;

			final int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				activeList = rightList;
			} else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
				activeList = null;
			}

			return false;
		});

		leftList.setOnTouchListener((v, event) -> {

			if (activeList != null && activeList != leftList) return true;

			final int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				activeList = leftList;
			} else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
				activeList = null;
			}

			return false;
		});
	}

	public void setData(List<Mergable> leftData, List<Mergable> rightData, ViewItemFactory viewFactory) {
		leftAdapter = new MergeAdapter(leftData, viewFactory);
		rightAdapter = new MergeAdapter(rightData, viewFactory);

		leftList.setAdapter(leftAdapter);
		rightList.setAdapter(rightAdapter);

		leftAdapter.setClickHandler(new MergeAdapter.ClickHandler() {
			@Override public void onClicked(Mergable data) {
				delete(data);
			}
		});

		rightAdapter.setClickHandler(new MergeAdapter.ClickHandler() {
			@Override public void onClicked(Mergable data) {

			}
		});

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

	private void delete(Mergable data) {
		int position = leftAdapter.getPosition(data);
		leftAdapter.delete(data);
		leftAdapter.notifyItemRemoved(position);
	}

	private void remove(Mergable data) {

	}
}
