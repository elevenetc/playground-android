package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import su.levenetc.androidplayground.presenter.RotationViewPresenter;
import su.levenetc.androidplayground.views.RotationSensorView;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class AlarmActivity extends AppCompatActivity {

	private RotationViewPresenter presenter;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RotationSensorView view = new RotationSensorView(this);
		presenter = new RotationViewPresenter();
		presenter.setView(view);
		setContentView(view);
	}
}
