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
		leftList.setAdapter(new MergeAdapter(leftData, viewFactory));
		rightList.setAdapter(new MergeAdapter(rightData, viewFactory));
		PairBuilder pairBuilder = new PairBuilder(leftData, rightData);

		separator.buildPairs(pairBuilder.getPairs());
		separator.setLists(leftList, rightList);
	}
}
