package su.levenetc.androidplayground.activities;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import su.levenetc.androidplayground.utils.ViewUtils;

/**
 * Created by eugene.levenetc on 21/11/2016.
 */

public class NfcReaderActivity extends AppCompatActivity {

    public static final int READ_CARD_FLAGS = NfcAdapter.FLAG_READER_NFC_A
            | NfcAdapter.FLAG_READER_NFC_B
            | NfcAdapter.FLAG_READER_NFC_F
            | NfcAdapter.FLAG_READER_NFC_V
            | NfcAdapter.FLAG_READER_NFC_BARCODE
            | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    public static final int READER_CHECK_DELAY = 100;


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
        defaultAdapter.enableReaderMode(
                this, new NfcAdapter.ReaderCallback() {
                    @Override
                    public void onTagDiscovered(Tag tag) {
                        startActivity(new Intent(NfcReaderActivity.this, JustActivity.class));
                    }
                },
                READ_CARD_FLAGS,
                getReadCardOptions()
        );
    }

    public static Bundle getReadCardOptions() {
        Bundle result = new Bundle();
        result.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, READER_CHECK_DELAY);
        return result;
    }
}
