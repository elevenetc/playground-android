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
import su.levenetc.androidplayground.queryline.nodes.AutoCompleteNode;
import su.levenetc.androidplayground.queryline.nodes.Node;
import su.levenetc.androidplayground.queryline.nodes.SpaceNode;
import su.levenetc.androidplayground.queryline.nodes.StaticNode;
import su.levenetc.androidplayground.utils.SystemUtils;

import java.util.HashSet;

public class QueryLine extends View {

	public static final String TAG = QueryLine.class.getSimpleName();

	private boolean edit;
	StringBuilder sb = new StringBuilder();
	QueryModel queryModel;

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
				final AutoCompleteNode result = new AutoCompleteNode(new HashSet<String>() {{
					add("hello");
					add("father");
				}});
				return bindDrawerAndNode(result, new AutoCompleteDrawer());
			}

			@Override public SpaceNode space() {
				return bindDrawerAndNode(new SpaceNode(), new SpaceNodeDrawer());
			}

			@Override public StaticNode staticNode() {
				return bindDrawerAndNode(new StaticNode(), new StaticNodeDrawer());
			}

			<N extends Node> N bindDrawerAndNode(N node, BaseNodeDrawer<N> drawer) {
				node.setQueryModel(queryModel);
				node.setDrawer(drawer);
				drawer.setNode(node);
				drawer.setQueryModel(queryModel);
				return node;
			}
		});

		setFocusableInTouchMode(true);
		setFocusable(true);

		setOnKeyListener((v, keyCode, event) -> {
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				if (keyCode == 66) {
					completeCurrent();
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
