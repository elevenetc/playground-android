package su.levenetc.androidplayground.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BootActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(AllEventReceiverActivity.class);
    }

    private void startActivity(Class c) {
        startActivity(new Intent(this, c));
        finish();
    }
}