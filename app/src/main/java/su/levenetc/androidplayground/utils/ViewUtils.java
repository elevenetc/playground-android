package su.levenetc.androidplayground.utils;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.*;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Eugene Levenetc on 16/07/2016.
 */

public class ViewUtils {

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
	private static int screenWidth = -1;
	private static int screenHeight = -1;
	private static Point screenSize;
	private static float defaultToolbarHeight;

	public static View findViewById(ViewGroup container, String id) {

		if (id == null || id.isEmpty()) return null;

		id = id.split("/")[1];

		Resources resources = container.getResources();
		String packageName = container.getContext().getPackageName();
		int intId = resources.getIdentifier(id, "id", packageName);
		return container.findViewById(intId);
	}

	/**
	 * @return "[package]:id/[xml-id]"
	 * where [package] is your package and [xml-id] is id of view
	 * or no-id if there is no id
	 */
	public static String getId(View view) {
		if (view.getId() == 0xffffffff) return "no-id";
		else return view.getResources().getResourceName(view.getId());
	}

	public static void drawDebugTree(View view, Canvas canvas, float offsetX, float offsetY) {

		float x = view.getX() + offsetX;
		float y = view.getY() + offsetY;
		int width = view.getWidth();
		int height = view.getHeight();

		if (view instanceof ViewGroup) {
			canvas.drawText(getLocString(x, y, width, height), x, y, Paints.Font.Black_9);
			canvas.drawRect(x, y, x + width, y + height, Paints.Stroke.Red);

			ViewGroup group = (ViewGroup) view;
			int childCount = group.getChildCount();

			for (int i = childCount - 1; i >= 0; i--) {
				drawDebugTree(group.getChildAt(i), canvas, x, y);
			}
		} else {
			canvas.drawText(getLocString(x, y, width, height), x, y, Paints.Font.Black_9);
			canvas.drawRect(x, y, x + width, y + height, Paints.Stroke.Blue);
		}
	}

	private static String getLocString(float x, float y, float width, float height) {
		return String.format("x: %f y: %f width: %f height: %f", x, y, width, height);
	}

	public static View getJustView(Context context, int color) {
		View result = new View(context);
		result.setBackgroundColor(color);
		return result;
	}

	public static PagerAdapter getFilledViewPagerAdapter(Context context, int size) {
		return new PagerAdapter() {

			@Override public int getCount() {
				return size;
			}

			@Override public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}

			@Override public Object instantiateItem(ViewGroup container, int position) {
				View view = getJustView(context, (position % 2 == 0 ? Color.RED : Color.BLUE));
				container.addView(view);
				return view;
			}
		};
	}

	public static RecyclerView getFilledRecyclerView(Context context) {
		RecyclerView result = new RecyclerView(context);
		result.setLayoutManager(new LinearLayoutManager(context));
		result.setAdapter(getFilledRecyclerViewAdapter());
		return result;
	}

	@NonNull public static RecyclerView.Adapter getFilledRecyclerViewAdapter() {
		return new RecyclerView.Adapter() {

			@Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				return new Holder(new TextView(parent.getContext()));
			}

			@Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
				((TextView) holder.itemView).setText(String.format("Item: %d", position));
			}

			@Override public int getItemCount() {
				return 100;
			}

			class Holder extends RecyclerView.ViewHolder {

				Holder(TextView itemView) {
					super(itemView);
				}
			}
		};
	}

	public static ViewGroup.LayoutParams viewGroupMW() {
		return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	public static LinearLayout.LayoutParams linearWM() {
		LinearLayout.LayoutParams result = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		return result;
	}

	public static LinearLayout.LayoutParams linearWM(int weight) {
		LinearLayout.LayoutParams result = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				weight);
		return result;
	}

	public static TextView createTextView(Context context, String text) {
		TextView result = new TextView(context);
		result.setText(text);
		return result;
	}

	public static void setTextSize(Paint paint, float dpValue, Context context) {
		paint.setTextSize(
				TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						dpValue, context.getResources().getDisplayMetrics())
		);
	}

	public static ViewGroup.LayoutParams matchParent() {
		return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
	}

	public static FrameLayout.LayoutParams wrapFrame() {
		return new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	public static String motionEventToString(MotionEvent event) {
		int action = event.getAction();
		String result;
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				result = "Down";
				break;
			case MotionEvent.ACTION_UP:
				result = "Up";
				break;
			case MotionEvent.ACTION_CANCEL:
				result = "Cancel";
				break;
			case MotionEvent.ACTION_MOVE:
				result = "Move";
				break;
			default:
				result = "Other";
		}

		return result;
	}

	public static float dpToPx(float dp) {
		if (dp <= 0) return 0;
		Resources r = Resources.getSystem();
		return dpToPx(dp, r);
	}

	public static float dpToPx(Context context, float dp) {
		if (dp <= 0) return 0;
		Resources r = context.getResources();
		return dpToPx(dp, r);
	}

	public static float dpToPx(float dp, Context context) {
		if (dp <= 0) return 0;
		Resources r = context.getResources();
		return dpToPx(dp, r);
	}

	private static float dpToPx(float dp, Resources resources) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
	}

	@TargetApi(21) public static void removeElevation(View view) {
		if (SystemUtils.isLollipop()) {
			view.setElevation(0);
			view.setStateListAnimator(null);
		}
	}

	public static float pxToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return px / (metrics.densityDpi / 160f);
	}

	public static void setHeight(View view, int height) {
		view.setLayoutParams(applyHeight(view, height));
	}

	public static ViewGroup.LayoutParams applyHeight(View view, int height) {
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		lp.height = height;
		return lp;
	}

	public static void postScroll(final ScrollView scrollView, final int position) {
		scrollView.post(new Runnable() {
			@Override public void run() {
				scrollView.scrollTo(0, position);
			}
		});
	}

	public static void setHeight(View view, float height) {
		setHeight(view, (int) height);
	}

	public static boolean isEmpty(TextView textView) {
		return textView.getText().toString().isEmpty();
	}

	public static boolean isViewContains(View view, int touchX, int touchY) {
		int[] l = new int[2];
		view.getLocationOnScreen(l);
		int x = l[0];
		int y = l[1];
		int w = view.getWidth();
		int h = view.getHeight();

		return isContains(touchX, touchY, x, y, w, h);
	}

	public static boolean isContains(float touchX, float touchY, float x, float y, float w, float h) {
		return isContains((int) touchX, (int) touchY, (int) x, (int) y, (int) w, (int) h);
	}

	public static boolean isContains(int touchX, int touchY, int x, int y, int w, int h) {
		if (touchX < x || touchX > x + w || touchY < y || touchY > y + h) {
			return false;
		} else {
			return true;
		}
	}

	public static void setColorState(TextView textView, int resColorId) {
		Resources resources = textView.getContext().getResources();
		XmlResourceParser parser = resources.getXml(resColorId);
		try {
			textView.setTextColor(ColorStateList.createFromXml(resources, parser));
		} catch (XmlPullParserException | IOException e) {
			e.printStackTrace();
		}
	}

	public static int getScreenHeight(Context context) {
		initScreenSize(context);
		return screenHeight;
	}

	private static void initScreenSize(Context context) {
		Point size = new Point();

		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getSize(size);
		screenWidth = size.x;
		screenHeight = size.y;
	}

	public static int getScreenWidth(Context context) {
		initScreenSize(context);
		return screenWidth;
	}

	public static int getHorizontalWidth(Context context) {
		int orientation = SystemUtils.getScreenOrientation(context);

		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			return getScreenWidth(context);
		} else {
			return getScreenWidth(context);
		}
	}

	public static Point getScreenSize(Context context) {
		if (screenSize == null) {
			screenSize = new Point();
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			windowManager.getDefaultDisplay().getSize(screenSize);
		}

		return screenSize;
	}

	public static void handleGlobalLayout(final GlobalLayoutListener listener, final View view) {
		handleGlobalLayout(listener, view, true);
	}

	public static void handleGlobalLayout(
			@NonNull final GlobalLayoutListener listener,
			@NonNull final View view,
			final boolean unsubscribe
	) {
		view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override public void onGlobalLayout() {
				if (unsubscribe) removeLayoutListener(view.getViewTreeObserver(), this);
				listener.onMeasure();
			}
		});
	}

	public static void removeLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			observer.removeGlobalOnLayoutListener(listener);
		} else {
			observer.removeOnGlobalLayoutListener(listener);
		}
	}

	public static boolean contains(float x, float y, View view, View topParent) {
		int[] loc = new int[2];
		int[] parentLoc = new int[2];
		Rect rectLocalVisible = new Rect();
		view.getLocalVisibleRect(rectLocalVisible);
		view.getLocationInWindow(loc);

		topParent.getLocationOnScreen(parentLoc);

		float viewX = loc[0];
		float viewY = loc[1];
		rectLocalVisible.offset((int) viewX - parentLoc[0], (int) viewY - parentLoc[1]);
		boolean result = rectLocalVisible.contains((int) x, (int) y);
		return result;
	}

	public static int generateViewId() {
		for (; ; ) {
			final int result = sNextGeneratedId.get();
			// aapt-generated IDs have the high byte nonzero; clamp to the range under that.
			int newValue = result + 1;
			if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
			if (sNextGeneratedId.compareAndSet(result, newValue)) {
				return result;
			}
		}
	}

	public static BitmapDrawable setTintDrawable(BitmapDrawable drawable,
	                                             Resources res,
	                                             @ColorRes int colorResId) {
		int color = res.getColor(colorResId);
		drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
		return drawable;
	}

	public static Bitmap tintBitmap(Resources resources, int drawableRes, int colorRes) {
		Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableRes);
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		Bitmap result = Bitmap.createBitmap(width, height, bitmap.getConfig());
		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setColorFilter(new PorterDuffColorFilter(resources.getColor(colorRes), PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, 0f, 0f, paint);
		return result;
	}

	public static Bitmap scaleBitmap(Bitmap bitmap, float scale) {
		int width = (int) (bitmap.getWidth() * scale);
		int height = (int) (bitmap.getHeight() * scale);
		return Bitmap.createScaledBitmap(
				bitmap,
				width,
				height,
				true
		);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	public static float getToolbarDefaultHeight(Context context) {
		if (defaultToolbarHeight == 0) {
			TypedValue tv = new TypedValue();
			context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
			defaultToolbarHeight = Resources.getSystem().getDimensionPixelSize(tv.resourceId);
		}
		return defaultToolbarHeight;
	}

	public static Path roundedRect(float left, float top, float right, float bottom, float rx, float ry, boolean conformToOriginalPost) {
		Path path = new Path();
		if (rx < 0) rx = 0;
		if (ry < 0) ry = 0;
		float width = right - left;
		float height = bottom - top;
		if (rx > width / 2) rx = width / 2;
		if (ry > height / 2) ry = height / 2;
		float widthMinusCorners = (width - (2 * rx));
		float heightMinusCorners = (height - (2 * ry));

		path.moveTo(right, top + ry);
		path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
		path.rLineTo(-widthMinusCorners, 0);
		path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
		path.rLineTo(0, heightMinusCorners);

		if (conformToOriginalPost) {
			path.rLineTo(0, ry);
			path.rLineTo(width, 0);
			path.rLineTo(0, -ry);
		} else {
			path.rQuadTo(0, ry, rx, ry);//bottom-left corner
			path.rLineTo(widthMinusCorners, 0);
			path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
		}

		path.rLineTo(0, -heightMinusCorners);

		path.close();//Given close, last lineto can be removed.

		return path;
	}

	public static float getDimen(Context context, @DimenRes int dimenResId) {
		return context.getResources().getDimension(dimenResId);
	}


	public interface GlobalLayoutListener {
		void onMeasure();
	}
}
