package su.levenetc.androidplayground.di;

import android.content.Context;

import su.levenetc.androidplayground.modules.AlarmModule;

/**
 * Created by Eugene Levenetc on 13/07/2016.
 */
public class DIHelper {

	private static AlarmAppComponent alarmAppComponent;

	public static void init(Context context) {
		alarmAppComponent = DaggerAlarmAppComponent.builder()
				.alarmModule(new AlarmModule(context))
				.build();
	}

	public static AlarmAppComponent getAlarmAppComponent() {
		return alarmAppComponent;
	}
}
