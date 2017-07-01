package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import su.levenetc.androidplayground.utils.ViewUtils;
import su.levenetc.androidplayground.views.UIGarden;

/**
 * Created by eugene.levenetc on 01/07/2017.
 */
public class UIGardenActivity extends AppCompatActivity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new UIGarden(this), ViewUtils.matchParent());
	}
}
