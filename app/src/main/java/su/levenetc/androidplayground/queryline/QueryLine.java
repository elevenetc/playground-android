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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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
					add("select");
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

			@Override public Node next() {
				final int size = queryModel.size();
				if (size == 0) {
					return bindDrawerAndNode(new SQLiteInitNode(), new AutoCompleteDrawer());
				} else if (size == 1) {
					return bindDrawerAndNode(new SQLiteColumnsNode(), new AutoCompleteDrawer());
				} else {
					return bindDrawerAndNode(new SQLiteColumnsNode(), new AutoCompleteDrawer());
				}
			}


			<N extends Node> N bindDrawerAndNode(N node, BaseNodeDrawer<N> drawer) {
				node.setQueryModel(queryModel);
				node.setDrawer(drawer);
				drawer.setNode(node);
				drawer.setQueryModel(queryModel);
				return node;
			}
		});

		List<NodeBuildAction> actionsList = new LinkedList<>();
		actionsList.add(new NodeBuildAction(queryModel, SQLiteInitNode::new, AutoCompleteDrawer::new));
		actionsList.add(new NodeBuildAction(queryModel, SQLiteColumnsNode::new, AutoCompleteDrawer::new));
		
		queryModel.addNode(actionsList.get(0).build());

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

	static class NodeBuildAction {

		QueryModel queryModel;
		NodeBuilder nodeBuilder;
		DrawerBuilder drawerBuilder;

		public NodeBuildAction(QueryModel queryModel, NodeBuilder nodeBuilder, DrawerBuilder drawerBuilder) {
			this.queryModel = queryModel;
			this.nodeBuilder = nodeBuilder;
			this.drawerBuilder = drawerBuilder;
		}

		Node build() {
			final Node node = nodeBuilder.build();
			final BaseNodeDrawer drawer = drawerBuilder.build();
			node.setQueryModel(queryModel);
			node.setDrawer(drawer);
			drawer.setQueryModel(queryModel);
			drawer.setNode(node);
			return node;
		}
	}

	interface NodeBuilder {
		Node build();
	}

	interface DrawerBuilder {
		BaseNodeDrawer build();
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
