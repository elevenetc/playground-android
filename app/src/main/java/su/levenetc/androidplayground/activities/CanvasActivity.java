package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import su.levenetc.androidplayground.views.CanvasView;

/**
 * Created by eugene.levenetc on 27/12/2016.
 */
public class CanvasActivity extends AppCompatActivity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new CanvasView(this));
	}
}
