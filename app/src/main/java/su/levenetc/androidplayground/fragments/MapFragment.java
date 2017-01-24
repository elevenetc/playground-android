package su.levenetc.androidplayground.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.models.MapLine;
import su.levenetc.androidplayground.models.MapLocation;
import su.levenetc.androidplayground.utils.MapUtils;
import su.levenetc.androidplayground.utils.ViewUtils;
import su.levenetc.androidplayground.views.MapEditorLayer;
import su.levenetc.androidplayground.views.MapWrapperLayout;

/**
 * Created by eugene.levenetc on 15/01/2017.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap map;
    private MapEditorLayer mapEditorLayer;
    private float dp256;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.map_editor_view, container, false);


        mapView = (MapView) result.findViewById(R.id.map_view);
        mapEditorLayer = (MapEditorLayer) result.findViewById(R.id.map_editor_layer);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        MapWrapperLayout mapWrapperLayout = (MapWrapperLayout) result.findViewById(R.id.wrapper_layout);

        mapWrapperLayout.setOnDragListener(new MapWrapperLayout.OnDragListener() {
            @Override
            public void onDrag(MotionEvent motionEvent) {
                refreshProjection();
            }
        });

        result.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshProjection();
            }
        });

        mapEditorLayer.setOnDragListener(new MapEditorLayer.OnDragListener() {
            @Override
            public void onDrag(MotionEvent motionEvent) {
                mapView.onTouchEvent(motionEvent);
                //refreshProjection();
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

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(true);
//		map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
//		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
//		map.animateCamera(cameraUpdate);

        mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                refreshProjection();
            }
        });

        mapView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                if (dragEvent == null) {

                }
                return true;
            }
        });

        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                refreshProjection();
            }
        });


        //setOnCameraMoveListener
        //map.

        List<LatLng> points = new ArrayList<>();
        List<MapLocation> mapLocations = new ArrayList<>();
        List<MapLocation> pathLocations = new ArrayList<>();
        points.add(new LatLng(80.384579, 4.894513));//ams//52.384579
//        points.add(new LatLng(52.384579, 24.894513));//pol
//        points.add(new LatLng(56.502100, 39.389472));//mos
//        points.add(new LatLng(52.424067, 59.851404));//kaz
        points.add(new LatLng(49.518260, 142.753098));//sackh

        MapLocation pathLocationRef = null;

        for (LatLng point : points) {
            addCircle(point);
            mapLocations.add(new MapLocation(point));

            //path init

            final MapLocation pathLocation = new MapLocation(point);

            if (pathLocationRef == null) {
                pathLocationRef = pathLocation;
            } else {
                pathLocationRef.next = pathLocation;
                pathLocation.previous = pathLocationRef;

                pathLocationRef = pathLocation;
            }

            pathLocations.add(pathLocation);
        }

        pathLocations.get(0).name = "Ams";
        pathLocations.get(1).name = "Sackh";

        mapEditorLayer.setMapLocations(mapLocations);
        mapEditorLayer.setMapLine(new MapLine(pathLocations));

        dp256 = ViewUtils.dpToPx(getContext(), 256);

    }

    private void refreshProjection() {

        //TODO: fix bearing
        //No need to calculate points: calculate distance once between points and change screen location according zoom and one visible point
        float zoom = map.getCameraPosition().zoom;
        float bearing = map.getCameraPosition().bearing;
        double eqLenght = MapUtils.equatorLength(dp256, zoom);

        mapEditorLayer.updateProjection(map.getProjection(), eqLenght);
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


    }
}
