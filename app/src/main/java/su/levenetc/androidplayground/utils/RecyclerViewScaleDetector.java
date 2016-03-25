package su.levenetc.androidplayground.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ScaleGestureDetector;

/**
 * Created by Eugene Levenetc on 26/03/2016.
 */
public class RecyclerViewScaleDetector extends ScaleGestureDetector {

	public static RecyclerViewScaleDetector create(Context context, final RecyclerView recyclerView, final ScaleHandler scaleHandler, final float minScale, final float maxScale) {
		return new RecyclerViewScaleDetector(context, new OnScaleGestureListener() {

			private int initScrollY;
			private float startFocusY;
			private float scaleY;

			@Override public boolean onScale(ScaleGestureDetector detector) {
				
				int scrollBy = computeScrollDiffOnScale(detector);
				Out.pln("onScale", "focusY:" + detector.getFocusY() + " scrollBy:" + scrollBy);

				float oldScale = scaleY;
				scaleY *= detector.getScaleFactor();
				scaleY = Math.max(minScale, Math.min(scaleY, maxScale));
				if (oldScale != scaleY) scaleHandler.onRecyclerViewScaleY(scaleY);
				return true;
			}

			private int computeScrollDiffOnScale(ScaleGestureDetector detector) {
				float yDiff = detector.getFocusY() - startFocusY;
				return (int) (recyclerView.computeVerticalScrollOffset() - initScrollY - yDiff);
			}

			@Override public boolean onScaleBegin(ScaleGestureDetector detector) {
				initScrollY = recyclerView.computeVerticalScrollOffset();
				startFocusY = detector.getFocusY();
				Out.pln("initScrollY:" + initScrollY + " startFocusY:" + startFocusY);
				return true;
			}

			@Override public void onScaleEnd(ScaleGestureDetector detector) {

			}
		});
	}

	private RecyclerViewScaleDetector(Context context, ScaleGestureDetector.OnScaleGestureListener listener) {
		super(context, listener);
	}

	public interface ScaleHandler {
		void onRecyclerViewScaleY(float value);

		void onRecyclerViewScaleX(float value);
	}
}
