package su.levenetc.androidplayground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import su.levenetc.androidplayground.models.UserModel;

public class MainActivity extends AppCompatActivity {

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView textView = (TextView) findViewById(R.id.text);

		UserModel user = UserModel.builder().birthday("10-10-1999").name("Bob").type(10).build();

		String birthday = user.birthday();
		String name = user.name();
		int type = user.type();
	}
}