package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.uigarden.UIGarden;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class UIGardenActivity extends AppCompatActivity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ui_garden);
		UIGarden garden = findViewById(R.id.ui_garden);
		View btnRestart = findViewById(R.id.btn_restart);
		btnRestart.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				garden.restart();
			}
		});

	}
}
