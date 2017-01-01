package su.levenetc.androidplayground.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.utils.ValueHandler;

/**
 * Created by eugene.levenetc on 01/01/2017.
 */

public class GLViewConfig extends LinearLayout {

    private View container;

    public GLViewConfig(Context context) {
        super(context);
        init();
    }

    public GLViewConfig(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_gl_config, this, true);
        container = findViewById(R.id.container);
        container.setVisibility(GONE);
        findViewById(R.id.btn_visibility).setOnClickListener(
                v -> container.setVisibility(container.getVisibility() == VISIBLE ? GONE : VISIBLE)
        );
    }

    public void setCamLocXHandler(ValueHandler valueHandler) {
        ((SeekBarExtended) findViewById(R.id.seek_cam_loc_x)).setValueHandler(valueHandler);
    }

    public void setCamLocYHandler(ValueHandler valueHandler) {
        ((SeekBarExtended) findViewById(R.id.seek_cam_loc_y)).setValueHandler(valueHandler);
    }

    public void setCamLocZHandler(ValueHandler valueHandler) {
        ((SeekBarExtended) findViewById(R.id.seek_cam_loc_z)).setValueHandler(valueHandler);
    }

    public void setCamLocTargetXHandler(ValueHandler valueHandler) {
        ((SeekBarExtended) findViewById(R.id.seek_cam_target_loc_x)).setValueHandler(valueHandler);
    }

    public void setCamLocTargetYHandler(ValueHandler valueHandler) {
        ((SeekBarExtended) findViewById(R.id.seek_cam_target_loc_y)).setValueHandler(valueHandler);
    }

    public void setCamLocTargetZHandler(ValueHandler valueHandler) {
        ((SeekBarExtended) findViewById(R.id.seek_cam_target_loc_z)).setValueHandler(valueHandler);
    }

    public void setUpXHandler(ValueHandler valueHandler) {
        ((SeekBarExtended) findViewById(R.id.seek_up_x)).setValueHandler(valueHandler);
    }

    public void setUpYHandler(ValueHandler valueHandler) {
        ((SeekBarExtended) findViewById(R.id.seek_up_y)).setValueHandler(valueHandler);
    }

    public void setUpZHandler(ValueHandler valueHandler) {
        ((SeekBarExtended) findViewById(R.id.seek_up_z)).setValueHandler(valueHandler);
    }
}
