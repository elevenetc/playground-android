package su.levenetc.androidplayground.raycaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class RayCasterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RayCasterView(this));
    }
}
