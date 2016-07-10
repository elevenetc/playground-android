package su.levenetc.androidplayground.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import su.levenetc.androidplayground.services.AlarmStatusService;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class AlarmActivity extends AppCompatActivity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startService(new Intent(this, AlarmStatusService.class));
	}
}
