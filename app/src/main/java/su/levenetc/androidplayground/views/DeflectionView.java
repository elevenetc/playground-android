package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import su.levenetc.androidplayground.utils.Utils;

/**
 * Created by Eugene Levenetc on 12/07/2016.
 */
public class DeflectionView extends View {

	private float value;
	private Paint redFill = new Paint();
	private Paint blackStroke = new Paint();
	private Rect valueTextBounds = new Rect();

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
		blackStroke.setTextSize(Utils.getSp(12, getContext()));
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		final int width = canvas.getWidth();
		final int height = canvas.getHeight();
		final int max = width / 2;
		final float defSize = max * Math.abs(value);
		final String stringValue = String.valueOf(value);
		final float left = value >= 0 ? max : max - defSize;
		final float right = value >= 0 ? max + defSize : max;

		canvas.drawRect(left, 0, right, height, redFill);
		canvas.drawRect(0, 0, width - 3, height - 3, blackStroke);
		canvas.drawRect(max, 0, max + 3, height, blackStroke);

		blackStroke.getTextBounds(stringValue, 0, stringValue.length(), valueTextBounds);

		canvas.drawText(stringValue, 3, valueTextBounds.top * -1 + 3, blackStroke);
	}
}
