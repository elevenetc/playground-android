package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import su.levenetc.androidplayground.models.QueryModel;
import su.levenetc.androidplayground.utils.SystemUtils;

public class QueryLine extends View {

	public static final String TAG = QueryLine.class.getSimpleName();

	private boolean edit;
	StringBuilder sb = new StringBuilder();
	QueryModel queryModel = new QueryModel();

	public QueryLine(Context context) {
		super(context);
		init();
	}

	public QueryLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {

		queryModel.addAutoComplete("hello");
		queryModel.addAutoComplete("father");

		setFocusableInTouchMode(true);
		setFocusable(true);

		setOnKeyListener((v, keyCode, event) -> {
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				if (keyCode == 66) {
					apply();
				}
				Log.i(TAG, "keyCode:" + keyCode);
			}
			return true;
		});
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		queryModel.draw(canvas);
	}

	@Override public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			edit = true;
			requestFocus();
			SystemUtils.showKeyboard(this);
		}
		return true;
	}

	@Override public InputConnection onCreateInputConnection(EditorInfo outAttrs) {

		outAttrs.actionLabel = null;
		outAttrs.label = "TEST TEXT";
		outAttrs.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
		outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE;

		return new MyInputConnection(this, true);
	}

	private void append(CharSequence string) {
		queryModel.append(String.valueOf(string));
	}

	private void deleteLast() {
		queryModel.deleteLast();
		invalidate();
	}

	private void apply() {

	}

	class MyInputConnection extends BaseInputConnection {

		private SpannableStringBuilder spannavleSb;

		public MyInputConnection(View targetView, boolean fullEditor) {
			super(targetView, fullEditor);
		}

		@Override public boolean deleteSurroundingText(int beforeLength, int afterLength) {
			//return super.deleteSurroundingText(beforeLength, afterLength);
			deleteLast();
			invalidate();
			return true;
		}

		@Override public boolean deleteSurroundingTextInCodePoints(int beforeLength, int afterLength) {
			//
			return true;
		}

		public Editable getEditable() {
			if (spannavleSb == null) {
				spannavleSb = (SpannableStringBuilder) Editable.Factory.getInstance().newEditable("Placeholder");
			}
			return spannavleSb;
		}

		public boolean commitText(CharSequence text, int newCursorPosition) {
			//spannavleSb.append(text);
			//sb.append(text);
			append(text);
			invalidate();
			return false;
		}
	}
}
