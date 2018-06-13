package su.levenetc.androidplayground.debug.menu.items;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class CheckBoxItem extends MenuItem {

    private final String title;
    private boolean checked;
    private Listener listener;

    public CheckBoxItem(String title, boolean checked, Listener listener) {
        this.title = Objects.requireNonNull(title);
        this.listener = Objects.requireNonNull(listener);
        this.checked = checked;
    }

    @Override
    public View buildView(Context context) {
        LinearLayout group = new LinearLayout(context);
        group.setOrientation(LinearLayout.HORIZONTAL);

        TextView textTitle = new TextView(context);
        textTitle.setText(title);

        CheckBox checkBox = new CheckBox(context);
        checkBox.setChecked(checked);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listener.onCheckedChanged(isChecked);
        });

        group.addView(textTitle);
        group.addView(checkBox);

        return group;
    }

    public interface Listener {
        void onCheckedChanged(boolean checked);
    }
}