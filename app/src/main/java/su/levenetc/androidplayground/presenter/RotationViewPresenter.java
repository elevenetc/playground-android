package su.levenetc.androidplayground.presenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import su.levenetc.androidplayground.di.DIHelper;
import su.levenetc.androidplayground.manager.SManager;
import su.levenetc.androidplayground.models.SManagerState;
import su.levenetc.androidplayground.views.RotationSensorView;

/**
 * Created by Eugene Levenetc on 12/07/2016.
 */
public class RotationViewPresenter {

	private RotationSensorView view;
	@Inject SManager sManager;
	private Subscription subscribtion;

	public RotationViewPresenter() {
		DIHelper.getAlarmAppComponent().inject(this);

	}

	public void onCreate(final RotationSensorView view) {
		this.view = view;
		subscribtion = sManager.getStateObservable()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<SManagerState>() {
					@Override public void call(SManagerState sManagerState) {
						view.setValues(sManagerState.x, sManagerState.y, sManagerState.z);
					}
				}, new Action1<Throwable>() {
					@Override public void call(Throwable throwable) {
						throwable.printStackTrace();
					}
				});
	}

	public void onDestroy() {
		if (subscribtion != null) subscribtion.unsubscribe();
	}
}