package su.levenetc.androidplayground.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.utils.ValueHandler;
import su.levenetc.androidplayground.utils.Utils;

/**
 * Created by eugene.levenetc on 01/01/2017.
 */

public class SeekBarExtended extends LinearLayout {

    private static final float MAX_VALUE = 200f;

    private TextView textTitle;
    private SeekBar seekBar;
    private String title;
    private ValueHandler valueHandler;
    private float value;

    public SeekBarExtended(Context context) {
        super(context);
        init();
    }

    public SeekBarExtended(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SeekBarExtended);
        title = a.getString(R.styleable.SeekBarExtended_seek_bar_title);
        init();
    }

    public void setValueHandler(ValueHandler valueHandler) {
        this.valueHandler = valueHandler;
    }

    private void init() {
        setOrientation(HORIZONTAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_seekbar_extended, this, true);
        textTitle = findViewById(R.id.text_title);
        seekBar = findViewById(R.id.seek_bar);

        if (title == null) title = "Undefined";

        textTitle.setTextColor(Color.WHITE);

        setBackgroundColor(Color.parseColor("#54000000"));

        seekBar.setMax((int) MAX_VALUE);
        seekBar.setProgress((int) (MAX_VALUE / 2));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateValue();
                if (valueHandler != null) valueHandler.onChange(value);
                updateTitleValue();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        updateTitleValue();
    }

    private void updateValue() {
        value = seekBar.getProgress() / 100f - 1f;
    }

    private void updateTitleValue() {
        String progress = Utils.formatFloat(value, "#.##");
        textTitle.setText(String.format("%s: %s", title, progress));
    }

}
