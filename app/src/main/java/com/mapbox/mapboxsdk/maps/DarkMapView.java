package com.mapbox.mapboxsdk.maps;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import su.levenetc.androidplayground.utils.Paints;

import java.lang.reflect.Field;

public class DarkMapView extends MapView {

	public DarkMapView(@NonNull Context context) {
		super(context);
		init();
	}

	public DarkMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setWillNotDraw(false);
		setStyleUrl("mapbox://styles/eugenelevenetc/cjaazs8w22x332rnxtwwjpr0h");

		postDelayed(new Runnable() {
			@Override public void run() {
				getFields();
			}
		}, 1000);


//		mapGestureDetector.setOnFlingListener(new MapboxMap.OnFlingListener() {
//			@Override public void onFling() {
//
//			}
//		});
	}

	private void getFields() {
		try {
			Field[] declaredFields = MapView.class.getDeclaredFields();
			Field f = MapView.class.getDeclaredField("mapGestureDetector");
			f.setAccessible(true);
			Object o = f.get(this);
			MapGestureDetector mapGestureDetector = (MapGestureDetector) o;
			mapGestureDetector.setOnFlingListener(new MapboxMap.OnFlingListener() {
				@Override public void onFling() {

				}
			});

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}


	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(0, 0, 100, 100, Paints.Fill.Red);
		canvas.drawRect(100, 100, 100, 100, Paints.Fill.Black);
	}

	@Override public void onDrawForeground(Canvas canvas) {
		super.onDrawForeground(canvas);
	}


}
