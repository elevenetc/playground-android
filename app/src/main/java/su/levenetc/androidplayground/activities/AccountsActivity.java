package su.levenetc.androidplayground.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import su.levenetc.androidplayground.utils.Out;

import static android.Manifest.permission.GET_ACCOUNTS;

/**
 * Created by eleven on 25/09/2016.
 */
public class AccountsActivity extends Activity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final AccountManager accountManager = AccountManager.get(this);

		if (ContextCompat.checkSelfPermission(this, GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {

			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this, GET_ACCOUNTS)) {

			} else {
				ActivityCompat.requestPermissions(this, new String[]{GET_ACCOUNTS}, 13);
			}


		} else {
			Account[] accounts = accountManager.getAccounts();
			for (Account account : accounts) {
				try {
					Out.pln(accountManager.getPassword(account));
				} catch (Exception e) {
					Out.pln("Error: " + account);
				}
			}
		}
	}
}