package su.levenetc.androidplayground;

import android.app.Application;

import com.facebook.stetho.Stetho;

import su.levenetc.androidplayground.di.DIHelper;
import su.levenetc.androidplayground.services.SensorsDataCollectorService;

/**
 * Created by Eugene Levenetc on 12/05/2016.
 */
public class App extends Application {
	@Override public void onCreate() {
		super.onCreate();
		//startService(new Intent(this, AlarmStatusService.class));
		DIHelper.init(this);

		Stetho.initializeWithDefaults(this);
		SensorsDataCollectorService.start(this);
	}
}