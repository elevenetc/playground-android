package su.levenetc.androidplayground.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.views.MapEditorLayer;

/**
 * Created by eugene.levenetc on 15/01/2017.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

	MapView mapView;
	GoogleMap map;
	private MapEditorLayer mapEditorLayer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.map_editor_view, container, false);

		// Gets the MapView from the XML layout and creates it
		mapView = (MapView) result.findViewById(R.id.map_view);
		mapEditorLayer = (MapEditorLayer) result.findViewById(R.id.map_editor_layer);
		mapView.onCreate(savedInstanceState);

		// Gets to GoogleMap from the MapView and does initialization stuff
		mapView.getMapAsync(this);

		result.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				refreshProjection();
			}
		});


		return result;
	}

	@Override
	public void onResume() {
		mapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override public void onMapReady(GoogleMap map) {
		this.map = map;
		map.getUiSettings().setMyLocationButtonEnabled(false);
//		map.getUiSettings().setMyLocationButtonEnabled(true);
		map.setMyLocationEnabled(true);

		// Needs to call MapsInitializer before doing any CameraUpdateFactory calls
		MapsInitializer.initialize(this.getActivity());

		// Updates the location and zoom of the MapView
//		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
//		map.animateCamera(cameraUpdate);

		mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override public void onGlobalLayout() {
				refreshProjection();
			}
		});

		addCircle(new LatLng(52.384579, 4.894513));
		addCircle(new LatLng(52.384579, 24.894513));
	}

	private void refreshProjection() {
		mapEditorLayer.updateProjection(map.getProjection());
	}

	private void addCircle(LatLng latLng) {
		CircleOptions circleOptions = new CircleOptions();
		circleOptions.center(latLng);
//		circleOptions.center(new LatLng(0, 0));
		circleOptions.radius(300000);
		circleOptions.strokeColor(Color.BLACK);
		circleOptions.fillColor(0x30ff0000);
		circleOptions.strokeWidth(2);
		map.addCircle(circleOptions);


		mapEditorLayer.addPoint(latLng);
	}
}
