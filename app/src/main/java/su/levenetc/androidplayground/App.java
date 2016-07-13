package su.levenetc.androidplayground;

import android.app.Application;
import android.content.Intent;

import su.levenetc.androidplayground.di.DIHelper;
import su.levenetc.androidplayground.services.AlarmStatusService;

/**
 * Created by Eugene Levenetc on 12/05/2016.
 */
public class App extends Application {
	@Override public void onCreate() {
		super.onCreate();
		startService(new Intent(this, AlarmStatusService.class));
		DIHelper.init(this);
	}
}
