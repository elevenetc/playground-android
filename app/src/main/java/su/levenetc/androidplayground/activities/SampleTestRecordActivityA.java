package su.levenetc.androidplayground.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import su.levenetc.androidplayground.R;

/**
 * Created by Eugene Levenetc on 02/10/2016.
 */

public class SampleTestRecordActivityA extends Activity {

	@BindView(R.id.btn_open_activity) Button btnOpenActivity;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_record_a);
		ButterKnife.bind(this);

		btnOpenActivity.setVisibility(View.GONE);
	}

	@OnClick(R.id.btn_show_button) public void handleShowButton() {
		btnOpenActivity.setVisibility(View.VISIBLE);
	}

	@OnClick(R.id.btn_open_activity) public void handleOpenActivity() {
		startActivity(new Intent(this, SampleTestRecordActivityB.class));
	}
}
