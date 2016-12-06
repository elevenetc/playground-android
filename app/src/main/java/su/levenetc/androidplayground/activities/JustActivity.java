package su.levenetc.androidplayground.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import su.levenetc.androidplayground.utils.ViewUtils;

/**
 * Created by eugene.levenetc on 21/11/2016.
 */

public class JustActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ViewUtils.createTextView(this, "Just Activity"));

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if(type == null){

        }
    }
}
