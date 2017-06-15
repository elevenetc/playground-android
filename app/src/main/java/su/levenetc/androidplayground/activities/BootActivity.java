package su.levenetc.androidplayground.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import su.levenetc.androidplayground.utils.Out;
import su.levenetc.androidplayground.utils.SystemUtils;

public class BootActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Out.pln("before:" + Build.CPU_ABI);

        SystemUtils.changeStaticField(Build.class, "CPU_ABI", "armeabi-vZZZa");

        //startActivity(StickyScrollViewActivity.class);
        startActivity(CanvasActivity.class);
    }

    private void startActivity(Class c) {
        startActivity(new Intent(this, c));
        finish();
    }
}