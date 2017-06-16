package su.levenetc.androidplayground.mergeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 15/06/2017.
 */
public class MergeViewSeparator extends View {

	private RecyclerView leftList;
	private RecyclerView rightList;
	private Paint red = new Paint();
	private Paint blue = new Paint();
	private Paint green = new Paint();
	private Paint yellowStroke = new Paint();

	List<PairView> pairViews = new LinkedList<>();
	private LinearLayoutManager leftLM;
	private LinearLayoutManager rightLM;

	public MergeViewSeparator(Context context) {
		super(context);
		init();
	}

	public MergeViewSeparator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		blue.setStyle(Paint.Style.FILL);
		blue.setColor(Color.BLUE);
		red.setStyle(Paint.Style.FILL);
		red.setColor(Color.RED);
		green.setStyle(Paint.Style.FILL);
		green.setColor(Color.GREEN);
		yellowStroke.setStyle(Paint.Style.STROKE);
		yellowStroke.setStrokeWidth(10);
		yellowStroke.setColor(Color.YELLOW);
	}

	public void setLists(RecyclerView leftList, RecyclerView rightList) {

		this.leftList = leftList;
		this.rightList = rightList;

		leftLM = (LinearLayoutManager) leftList.getLayoutManager();
		rightLM = (LinearLayoutManager) rightList.getLayoutManager();
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (PairView pairView : pairViews) {
			final View leftView = leftLM.findViewByPosition(pairView.leftIndex);
			final View rightView = rightLM.findViewByPosition(pairView.rightIndex);

			pairView.setVisible(
					leftLM.findFirstVisibleItemPosition(),
					leftLM.findLastVisibleItemPosition(),
					rightLM.findFirstVisibleItemPosition(),
					rightLM.findLastVisibleItemPosition()
			);

			if (leftView != null) leftView.setBackgroundColor(Color.RED);
			if (rightView != null) rightView.setBackgroundColor(Color.RED);

			pairView.drawConnection(leftView, rightView, canvas);
		}
	}

	public void buildPairs(List<Pair> pairs) {

		for (Pair pair : pairs) {
			pairViews.add(new PairView(pair.leftIndex, pair.rightIndex));
		}

	}

}
