package su.levenetc.androidplayground.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.maps.Projection;
import su.levenetc.androidplayground.models.MapLocation;
import su.levenetc.androidplayground.models.MapLine;
import su.levenetc.androidplayground.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eugene.levenetc on 16/01/2017.
 */
public class MapEditorLayer extends View {

	private List<MapLocation> mapLocations = new ArrayList<>();
	private Paint fillPaint = new Paint();
	private Paint strokePaint = new Paint();
	private Path path = new Path();
	private float[] linePoints;
	private MapLine mapLine;

	private OnDragListener dragListener;
	private Point screenSize;

	public MapEditorLayer(Context context) {
		super(context);
		init();
	}

	public MapEditorLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		screenSize = Utils.getScreenSize((Activity) getContext());
		if (mapLine != null) {
			mapLine.setScreenSize(screenSize);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}

	private void init() {
		fillPaint.setColor(Color.GREEN);
		fillPaint.setStyle(Paint.Style.FILL);

		strokePaint.setColor(Color.BLUE);
		strokePaint.setStrokeWidth(50);
		strokePaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		dragListener.onDrag(event);
		return super.dispatchTouchEvent(event);
	}

	public void setMapLine(MapLine mapLine) {
		this.mapLine = mapLine;
		if (screenSize != null)
			mapLine.setScreenSize(screenSize);
	}

	public void setMapLocations(List<MapLocation> mapLocations) {
		this.mapLocations = mapLocations;
	}

	public void updateProjection(Projection projection, double eqLenght) {

		for (MapLocation mapLocation : mapLocations) {
			final Point result = projection.toScreenLocation(mapLocation.geo);
			mapLocation.screen.set(result.x, result.y);
		}

		mapLine.updateScreenCoordinates(projection, eqLenght);

		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (MapLocation mapLocation : mapLocations) {
			canvas.drawCircle(mapLocation.screen.x, mapLocation.screen.y, 100, fillPaint);
		}

		canvas.drawLines(mapLine.getPath(), strokePaint);
	}

	public void setOnDragListener(OnDragListener dragListener) {
		this.dragListener = dragListener;
	}

	public interface OnDragListener {
		void onDrag(MotionEvent motionEvent);
	}
}
