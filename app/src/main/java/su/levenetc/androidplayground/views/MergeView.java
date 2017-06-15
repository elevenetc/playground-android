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
import su.levenetc.androidplayground.adapters.TextLinesAdapter;

/**
 * Created by eugene.levenetc on 15/06/2017.
 */
public class MergeView extends View {

	private RecyclerView leftList;
	private RecyclerView rightList;
	private Paint red = new Paint();
	private Paint blue = new Paint();
	private Paint green = new Paint();
	private Paint yellowStroke = new Paint();

	public MergeView(Context context) {
		super(context);
		init();
	}

	public MergeView(Context context, AttributeSet attrs) {
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
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), green);
		canvas.drawRect(0, 0, 10, 10, red);

		final LinearLayoutManager leftLM = (LinearLayoutManager) leftList.getLayoutManager();
		final LinearLayoutManager rightLM = (LinearLayoutManager) rightList.getLayoutManager();

		final TextLinesAdapter leftAdapter = (TextLinesAdapter) leftList.getAdapter();
		final TextLinesAdapter rightAdapter = (TextLinesAdapter) rightList.getAdapter();

		final View leftMarker = leftLM.findViewByPosition(leftAdapter.getIndex("bbb"));
		final View rightMarker = rightLM.findViewByPosition(rightAdapter.getIndex("bbb"));

		if (leftMarker != null) {
			final float top = leftMarker.getY();
			canvas.drawRect(0, top, canvas.getWidth() / 2, top + leftMarker.getHeight(), blue);
		}

		if (rightMarker != null) {
			final float top = rightMarker.getY();
			canvas.drawRect(canvas.getWidth() / 2, top, canvas.getWidth(), top + rightMarker.getHeight(), blue);
		}

		if (leftMarker != null && rightMarker != null) {

			float xLeft = 0;
			float yLeft = leftMarker.getY();
			float xRight = canvas.getWidth();
			float yRight = rightMarker.getY();

			float xLeftControl = canvas.getWidth() / 2;
			float yLeftControl = yLeft;

			float xRightControl = canvas.getWidth() / 2;
			float yRightControl = yRight;

			Path path = new Path();
			path.moveTo(xLeft, yLeft);
			path.cubicTo(xLeftControl, yLeftControl, xRightControl, yRightControl, xRight, yRight);
			canvas.drawPath(path, yellowStroke);
		}


	}
}
