package su.levenetc.androidplayground.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import su.levenetc.androidplayground.manager.SManager;

/**
 * Created by Eugene Levenetc on 13/07/2016.
 */
@Module
public class AlarmModule {
	private Context context;

	public AlarmModule(Context context) {
		this.context = context;
	}

	@Singleton @Provides public SManager provideSensorsManager() {
		SManager sManager = new SManager();
		sManager.init(context);
		return sManager;
	}
}
