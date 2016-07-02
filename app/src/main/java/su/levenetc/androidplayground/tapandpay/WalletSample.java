package su.levenetc.androidplayground.tapandpay;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wallet.Wallet;

/**
 * Created by Eugene Levenetc on 02/07/2016.
 */
public class WalletSample {
	public static void init(GoogleApiClient googleApiClient) {

		Wallet.Payments.isReadyToPay(googleApiClient).setResultCallback(
				new ResultCallback<BooleanResult>() {
					@Override
					public void onResult(@NonNull BooleanResult booleanResult) {

						//hideProgressDialog();

						if (booleanResult.getStatus().isSuccess()) {
							if (booleanResult.getValue()) {
								// Show Android Pay buttons and hide regular checkout button
								// ...
							} else {
								// Hide Android Pay buttons, show a message that Android Pay
								// cannot be used yet, and display a traditional checkout button
								// ...
							}
						} else {
							// Error making isReadyToPay call
							//Log.e(TAG, "isReadyToPay:" + booleanResult.getStatus());
						}
					}
				}
		);
	}
}
