package su.levenetc.androidplayground.uigarden;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import su.levenetc.androidplayground.utils.Paints;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class UIGarden extends View {

	private PathController controller;
	RandomCurve randomCurve;

	public UIGarden(Context context) {
		super(context);
		init();
	}

	public UIGarden(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		controller = new PathController(this);
	}

	@Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		randomCurve = new RandomCurve(50, 50, 400, 200, 4, 0.3f);

		//controller.init();
		//startDraw();
	}


	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), Paints.Fill.Grey);
		//canvas.drawPath(controller.getPath(), Paints.Stroke.White);
		//controller.drawDebug(canvas);
		randomCurve.debugDraw(canvas);
	}

	public void restart() {
		controller.reset();
		startDraw();
	}

	private void startDraw() {
		postDelayed(() -> controller.startDraw(), 500);
	}
}
