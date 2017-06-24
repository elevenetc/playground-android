package su.levenetc.androidplayground.mergeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 15/06/2017.
 */
public class MergeViewSeparator extends View {
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
		green.setStyle(Paint.Style.FILL);
		green.setColor(Color.GREEN);
		yellowStroke.setStyle(Paint.Style.STROKE);
		yellowStroke.setStrokeWidth(10);
		yellowStroke.setColor(Color.YELLOW);
	}

	public void setLists(RecyclerView leftList, RecyclerView rightList) {

		leftLM = (LinearLayoutManager) leftList.getLayoutManager();
		rightLM = (LinearLayoutManager) rightList.getLayoutManager();
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (PairView pairView : pairViews) {
			final View leftListItem = leftLM.findViewByPosition(pairView.leftIndex);
			final View rightListItem = rightLM.findViewByPosition(pairView.rightIndex);

			pairView.setVisible(
					leftLM.findFirstVisibleItemPosition(),
					leftLM.findLastVisibleItemPosition(),
					rightLM.findFirstVisibleItemPosition(),
					rightLM.findLastVisibleItemPosition()
			);

			if (leftListItem != null) {
				leftListItem.setBackgroundColor(Color.RED);
			}
			if (rightListItem != null) {
				rightListItem.setBackgroundColor(Color.RED);
			}

			pairView.drawConnection(leftListItem, rightListItem, canvas);
		}
	}

	public void buildPairs(List<Pair> pairs) {

		for (Pair pair : pairs) {
			pairViews.add(new PairView(pair.leftIndex, pair.rightIndex));
		}

	}

	public void replace(int position, Mergable data) {
		final Iterator<PairView> it = pairViews.iterator();
		while (it.hasNext()) {
			final PairView pair = it.next();
			if (pair.leftIndex == position) {
				it.remove();
				break;
			}
		}
	}
}
