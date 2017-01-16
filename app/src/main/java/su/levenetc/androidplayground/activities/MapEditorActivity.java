package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.fragments.MapFragment;

/**
 * Created by eugene.levenetc on 15/01/2017.
 */
public class MapEditorActivity extends AppCompatActivity {

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.root_container, new MapFragment())
					.commit();
		}
	}
}