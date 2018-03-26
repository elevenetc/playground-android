package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import su.levenetc.androidplayground.R;

public class DoubleMapBoxActivity extends AppCompatActivity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Mapbox.getInstance(this, "pk.eyJ1IjoiZXVnZW5lbGV2ZW5ldGMiLCJhIjoiY2l5ZWxyZXF2MDA5aDMzcXBzcHA5M2h6ZiJ9.MFk21zgnp3syT1f1UFvSsA");
		setContentView(R.layout.activity_double_mapbox);
		MapView mapView = (MapView) findViewById(R.id.mapView);
		mapView.onCreate(savedInstanceState);
	}
}
