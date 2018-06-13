package su.levenetc.androidplayground.debug.menu.items;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import su.levenetc.androidplayground.debug.menu.views.DividerView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DividerItem extends MenuItem {

    private final String title;

    public DividerItem() {
        title = null;
    }

    public DividerItem(String title) {
        this.title = title;
    }

    @Override
    public View buildView(Context context) {
        if (title == null) {

            return new DividerView(context);
        } else {

            LinearLayout group = new LinearLayout(context);
            group.setOrientation(LinearLayout.HORIZONTAL);
            TextView textTitle = new TextView(context);
            textTitle.setText(title);

            group.addView(textTitle, new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            group.addView(new DividerView(context), params);

            return group;
        }
    }
}
