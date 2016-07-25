package su.levenetc.androidplayground.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by eugene.levenetc on 25/07/16.
 */
public class AllEventReceiverActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();

        broadcastManager.registerReceiver(new All(), filter);

        broadcastManager.sendBroadcast(new Intent("x"));
        broadcastManager.sendBroadcast(new Intent("y"));
        broadcastManager.sendBroadcast(new Intent("*"));
    }

    private static class All extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(AllEventReceiverActivity.class.getSimpleName(), action);
        }
    }
}
