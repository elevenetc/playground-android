package su.levenetc.androidplayground.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * adb shell am broadcast -a su.elevenetc.android.intent.DEBUG_COMMAND -e "key" "value"
 */
public class DebugCommandsReceiver extends BroadcastReceiver {


	@Override public void onReceive(Context context, Intent intent) {
		String tag = "DebugCommandsReceiver";
		Log.d(tag, "received command");
		Log.d(tag, intent.getStringExtra("key"));
	}
}