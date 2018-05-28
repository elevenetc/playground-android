package su.levenetc.androidplayground.raytracer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import su.levenetc.androidplayground.debug.DebugMenu;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayTracerActivity extends AppCompatActivity {

    private String debugScene = "Debug Scene";
    private String debugLight = "Debug Light";
    private RayTracerView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new RayTracerView(this);
        setContentView(view);

        DebugMenu.add(this);

//        view.setDebugLight(true);
//        view.setDebugScene(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        createCheckBoxItem(debugScene, menu, false);
        createCheckBoxItem(debugLight, menu, false);
        return super.onCreateOptionsMenu(menu);
    }

    private void createCheckBoxItem(String text, Menu menu, boolean selected) {
        CheckBox checkbox = new CheckBox(this);
        checkbox.setChecked(selected);
        menu.add(text)
                .setCheckable(true)
                .setActionView(checkbox);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(debugScene)) {
            view.setDebugScene(!item.isChecked());
            item.setChecked(!item.isChecked());
            return true;
        } else if (item.getTitle().equals(debugLight)) {
            view.setDebugLight(!item.isChecked());
            item.setChecked(!item.isChecked());
            return true;
        } else {
            return onOptionsItemSelected(item);
        }
    }
}
