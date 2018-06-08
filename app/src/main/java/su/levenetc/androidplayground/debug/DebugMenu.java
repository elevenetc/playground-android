package su.levenetc.androidplayground.debug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DebugMenu {

    public static class Builder {

        private String title;
        private List<MenuItem> items = new LinkedList<>();

        public Builder setTitle(String title) {

            this.title = title;
            return this;
        }

        public Builder addDivider() {
            items.add(new Divider());
            return this;
        }

        public Builder addDivider(String title) {
            items.add(new Divider(title));
            return this;
        }

        public Builder addCheckBox(String text, boolean checked) {
            items.add(new CheckBox(text, checked));
            return this;
        }

        public void build(AppCompatActivity activity) {

            MenuContainer itemsContainer = new MenuContainer(activity);
            ContentFrameLayout contentView = activity.findViewById(android.R.id.content);

            if (title != null) {
                TextView textTitle = new TextView(activity);
                textTitle.setText(title);
                itemsContainer.addView(textTitle);
            }

            for (MenuItem item : items)
                itemsContainer.addView(item.buildView(activity), new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));

            contentView.addView(itemsContainer, new ContentFrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        }
    }

    static class Divider extends MenuItem {

        private final String title;

        public Divider() {
            title = null;
        }

        public Divider(String title) {
            this.title = title;
        }

        @Override
        public View buildView(Context context) {
            if (title == null) {
                return new DividedView(context);
            } else {
                LinearLayout group = new LinearLayout(context);
                group.setOrientation(LinearLayout.HORIZONTAL);
                TextView textTitle = new TextView(context);
                textTitle.setText(title);

                group.addView(textTitle, new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                group.addView(new DividedView(context), new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));

                return group;
            }
        }
    }

    static class DividedView extends View {

        private final float heightDp = 10;
        private final Paint paint = new Paint();

        public DividedView(Context context) {
            super(context);
            paint.setStrokeWidth(dpToPx(3));
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.GRAY);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            canvas.drawLine(0, height / 2, width, height / 2, paint);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            float height = dpToPx(heightDp);

            setMeasuredDimension(
                    MeasureSpec.getSize(widthMeasureSpec),
                    (int) height
            );
        }

        private float dpToPx(float dp) {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        }
    }

    static class CheckBox extends MenuItem {

        private final String title;
        private boolean checked;

        public CheckBox(String title, boolean checked) {
            this.title = title;
            this.checked = checked;
        }

        @Override
        public View buildView(Context context) {
            return new View(context);
        }
    }

    static abstract class MenuItem {
        public abstract View buildView(Context context);
    }

    public static void add(AppCompatActivity activity) {
        ContentFrameLayout view = activity.findViewById(android.R.id.content);
        view.addView(new MenuContainer(activity), new ContentFrameLayout.LayoutParams(200, 200));
    }

    static class MenuContainer extends LinearLayout {

        public MenuContainer(@NonNull Context context) {
            super(context);
            init();
        }

        private void init() {
            setOrientation(VERTICAL);
            setBackgroundColor(Color.BLUE);

        }
    }

    static class DM extends View {

        public DM(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(
                    MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY)
            );
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.RED);
        }
    }
}
