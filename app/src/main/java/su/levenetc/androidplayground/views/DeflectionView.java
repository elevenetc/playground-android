package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Eugene Levenetc on 12/07/2016.
 */
public class DeflectionView extends View {

	private float value;
	private Paint redFill = new Paint();
	private Paint blackStroke = new Paint();

	public DeflectionView(Context context) {
		super(context);
		init();
	}

	public DeflectionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		redFill.setStyle(Paint.Style.FILL);
		redFill.setColor(Color.RED);
		blackStroke.setStyle(Paint.Style.STROKE);
		blackStroke.setColor(Color.BLACK);
		blackStroke.setStrokeWidth(3);
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		int max = width / 2;
		float w = max * value;
		canvas.drawRect(width / 2, 0, max + w, height, redFill);
		canvas.drawRect(0, 0, width - 3, height - 3, blackStroke);
		canvas.drawRect(max, 0, max + 3, height, blackStroke);
	}
}
