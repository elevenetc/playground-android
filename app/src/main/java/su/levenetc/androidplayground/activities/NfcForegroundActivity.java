package su.levenetc.androidplayground.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import su.levenetc.androidplayground.utils.ViewUtils;

/**
 * Created by eugene.levenetc on 21/11/2016.
 */

public class NfcForegroundActivity extends AppCompatActivity {

    private static final IntentFilter[] INTENT_FILTER = new IntentFilter[]{
            new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
//            new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
//            new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
//            new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)
    };

    private static final String[][] TECH_LIST = new String[][]{
            {IsoDep.class.getName()}
    };
    private NfcAdapter defaultAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ViewUtils.createTextView(this, "NFC Activity"));
        defaultAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribeOnNfcEvents();
    }

    @Override
    protected void onPause() {
        unsubscribeFromNfcEvents();
        super.onPause();
    }

    private void unsubscribeFromNfcEvents() {
        defaultAdapter.disableForegroundDispatch(this);
    }

    private void subscribeOnNfcEvents() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, JustActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        defaultAdapter.enableForegroundDispatch(this, pendingIntent, INTENT_FILTER, TECH_LIST);
    }
}
