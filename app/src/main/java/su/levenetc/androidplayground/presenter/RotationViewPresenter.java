package su.levenetc.androidplayground.presenter;

import javax.inject.Inject;

import su.levenetc.androidplayground.di.DIHelper;
import su.levenetc.androidplayground.manager.SManager;
import su.levenetc.androidplayground.views.RotationSensorView;

/**
 * Created by Eugene Levenetc on 12/07/2016.
 */
public class RotationViewPresenter {

	private RotationSensorView view;
	@Inject SManager sManager;

	public RotationViewPresenter() {
		DIHelper.getAlarmAppComponent().inject(this);
	}

	public void setView(RotationSensorView view) {
		this.view = view;
	}
}