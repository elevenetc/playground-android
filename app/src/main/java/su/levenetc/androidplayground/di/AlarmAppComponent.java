package su.levenetc.androidplayground.di;

import javax.inject.Singleton;

import dagger.Component;
import su.levenetc.androidplayground.modules.AlarmModule;
import su.levenetc.androidplayground.presenter.RotationViewPresenter;
import su.levenetc.androidplayground.services.AlarmStatusService;

/**
 * Created by Eugene Levenetc on 13/07/2016.
 */
@Singleton
@Component(modules = AlarmModule.class)
public interface AlarmAppComponent {
	void inject(RotationViewPresenter rotationViewPresenter);
	void inject(AlarmStatusService service);
}
