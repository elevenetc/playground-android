package su.levenetc.androidplayground.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import su.ValueX;
import su.levenetc.androidplayground.R;

/**
 * Created by Eugene Levenetc on 02/10/2016.
 */

public class SampleTestRecordActivityB extends Activity {

	@BindView(R.id.edit_text) EditText editText;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_test_record_b);
		ButterKnife.bind(this);

		editText.setText("type:" + ValueX.A);
	}

	@OnClick(R.id.btn_action) public void handleAction() {
		if (editText.getText().toString().isEmpty()) {
			editText.setText("Should be filled");
		} else {
			editText.setEnabled(false);
		}
	}
}
