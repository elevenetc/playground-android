package su.levenetc.androidplayground.queryline;

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
import su.levenetc.androidplayground.queryline.drawers.*;
import su.levenetc.androidplayground.queryline.nodes.*;
import su.levenetc.androidplayground.utils.SystemUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class QueryLine extends View {

	public static final String TAG = QueryLine.class.getSimpleName();
	QueryModel queryModel;
	Map<Class, BaseNodeDrawer> drawers = new HashMap<>();

	public QueryLine(Context context) {
		super(context);
		init();
	}

	public QueryLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {


		queryModel = new QueryModel(new DrawersFactory() {
			@Override public AutoCompleteNode autoComplete() {
				return new AutoCompleteNode(new HashSet<String>() {{
					add("select");
					add("father");
				}});
			}

			@Override public SpaceNode space() {
				return new SpaceNode();
			}

			@Override public StaticNode staticNode() {
				return new StaticNode();
			}

			@Override public Node next() {
				final int size = queryModel.size();
				if (size == 0) {
					return new SQLiteInitNode();
				} else if (size == 1) {
					return new SQLiteColumnsNode();
				} else {
					return new SQLiteColumnsNode();
				}
			}
		});

		drawers.put(SQLiteColumnsNode.class, new AutoCompleteDrawer());
		drawers.put(SQLiteInitNode.class, new AutoCompleteDrawer());
		drawers.put(StaticNode.class, new StaticNodeDrawer());
		drawers.put(SpaceNode.class, new SpaceNodeDrawer());

		queryModel.addNode(new SQLiteInitNode());

		setFocusableInTouchMode(true);
		setFocusable(true);

		setOnKeyListener((v, keyCode, event) -> {
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				if (keyCode == 66) {
					completeCurrent();
				} else if (keyCode == 67) {
					deleteLast();
				} else if (keyCode == 61) {
					completeCurrent();
				} else {
					append((char) event.getUnicodeChar());
				}
				Log.i(TAG, "keyCode:" + keyCode + " - " + (char) event.getUnicodeChar());
			}
			return true;
		});
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (Node node : queryModel.getNodes()) {

			final BaseNodeDrawer drawer = drawers.get(node.getClass());
			drawer.setNode(node);
			drawer.setQueryModel(queryModel);
			drawer.measureLayoutDraw(canvas);
		}
	}

	@Override public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
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

	private void append(char chars) {
		queryModel.append(chars);
		invalidate();
	}

	private void deleteLast() {
		queryModel.deleteLast();
		invalidate();
	}

	private void completeCurrent() {
		queryModel.completeCurrent();
		invalidate();
	}

	class MyInputConnection extends BaseInputConnection {

		private SpannableStringBuilder spannavleSb;

		public MyInputConnection(View targetView, boolean fullEditor) {
			super(targetView, fullEditor);
		}

		@Override public boolean deleteSurroundingText(int beforeLength, int afterLength) {
			//return super.deleteSurroundingText(beforeLength, afterLength);
			Log.i(TAG, "delete char");
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
			final char ch = text.charAt(0);
			Log.i(TAG, "keyboard input:" + ch);
			append(ch);
			return false;
		}
	}
}
