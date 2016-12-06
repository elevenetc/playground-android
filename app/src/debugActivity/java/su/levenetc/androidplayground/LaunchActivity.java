package su.levenetc.androidplayground;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import su.levenetc.androidplayground.activities.HtmlSymbolsTest;

/**
 * Created by eugene.levenetc on 06/12/2016.
 */
public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, HtmlSymbolsTest.class));
    }
}
