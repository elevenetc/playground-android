package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.views.QueryLine;

public class QueryLineActivity extends AppCompatActivity {

	private QueryLine queryLine;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_queryline);
		queryLine = (QueryLine) findViewById(R.id.query_line);
	}

	@Override public void onBackPressed() {
		super.onBackPressed();
	}
}
