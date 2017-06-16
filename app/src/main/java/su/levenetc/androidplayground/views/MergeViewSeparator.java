package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import su.levenetc.androidplayground.mergeview.Pair;

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

			pairView.drawConnection(leftView, rightView, canvas);
		}
	}

	public void buildPairs(List<Pair> pairs) {

		for (Pair pair : pairs) {
			pairViews.add(new PairView(pair.leftIndex, pair.rightIndex));
		}

	}

	static class PairView {

		int leftIndex;
		int rightIndex;
		String value;
		Path path = new Path();

		Paint rectColor = new Paint();
		Paint pathColor = new Paint();

		public PairView(int leftIndex, int rightIndex) {

			this.leftIndex = leftIndex;
			this.rightIndex = rightIndex;

			rectColor.setStyle(Paint.Style.FILL);
			rectColor.setColor(Color.BLUE);

			pathColor.setStrokeWidth(5);
			pathColor.setStyle(Paint.Style.STROKE);
			pathColor.setColor(Color.YELLOW);
		}

		void drawConnection(View leftView, View rightView, Canvas canvas) {
			drawRect(leftView, rightView, canvas);
			drawPath(leftView, rightView, canvas);
		}

		void drawRect(View leftView, View rightView, Canvas canvas) {
			if (leftView != null) {
				final float top = leftView.getY();
				drawRect(0, top, top + leftView.getHeight(), canvas);
			}

			if (rightView != null) {
				final float top = rightView.getY();
				drawRect(canvas.getWidth() / 2, top, top + rightView.getHeight(), canvas);
			}
		}

		void drawPath(View leftView, View rightView, Canvas canvas) {

			float xLeft = 0;
			float yLeft = -1;
			float xRight = canvas.getWidth();
			float yRight = -1;

			float xLeftControl = canvas.getWidth() / 2;
			float yLeftControl = -1;

			float xRightControl = canvas.getWidth() / 2;
			float yRightControl = -1;

			if (leftView != null && rightView != null) {
				yLeft = leftView.getY();
				yRight = rightView.getY();
				yLeftControl = yLeft;
				yRightControl = yRight;
			} else if (leftView != null) {
				yLeft = leftView.getY();
				yRight = -10;
				yLeftControl = yLeft;
				yRightControl = yRight;
			} else if (rightView != null) {
				yLeft = -10;
				yRight = rightView.getY();
				yLeftControl = yLeft;
				yRightControl = yRight;
			}

			drawPath(xLeft, yLeft, xRight, yRight, xLeftControl, yLeftControl, xRightControl, yRightControl, canvas, pathColor);
		}

		void drawPath(float xLeft, float yLeft,
		              float xRight, float yRight,
		              float xLeftControl, float yLeftControl,
		              float xRightControl, float yRightControl,
		              Canvas canvas, Paint paint) {
			path.reset();
			path.moveTo(xLeft, yLeft);
			path.cubicTo(xLeftControl, yLeftControl, xRightControl, yRightControl, xRight, yRight);
			canvas.drawPath(path, paint);
		}

		void drawRect(float left, float top, float bottom, Canvas canvas) {
			canvas.drawRect(left, top, left + canvas.getWidth() / 2, bottom, rectColor);
		}
	}
}
