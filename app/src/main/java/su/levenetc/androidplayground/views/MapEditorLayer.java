package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.maps.Projection;

import java.util.ArrayList;
import java.util.List;

import su.levenetc.androidplayground.models.MapLocation;
import su.levenetc.androidplayground.models.MapPath;

/**
 * Created by eugene.levenetc on 16/01/2017.
 */
public class MapEditorLayer extends View {

    private List<MapLocation> mapLocations = new ArrayList<>();
    private Paint fillPaint = new Paint();
    private Paint strokePaint = new Paint();
    private Path path = new Path();
    private float[] linePoints;
    private MapPath mapPath;

    private OnDragListener dragListener;

    public MapEditorLayer(Context context) {
        super(context);
        init();
    }

    public MapEditorLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
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

    public void setMapPath(MapPath mapPath) {
        this.mapPath = mapPath;
    }

    public void setMapLocations(List<MapLocation> mapLocations) {
        this.mapLocations = mapLocations;
    }

    public void updateProjection(Projection projection) {

        for (MapLocation mapLocation : mapLocations) {
            final Point result = projection.toScreenLocation(mapLocation.geo);
            mapLocation.screen.set(result.x, result.y);
        }

        mapPath.updateScreenCoordinates(projection);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (MapLocation mapLocation : mapLocations) {
            canvas.drawCircle(mapLocation.screen.x, mapLocation.screen.y, 100, fillPaint);
        }

        canvas.drawLines(mapPath.getPath(), strokePaint);
    }

    public void setOnDragListener(OnDragListener dragListener) {
        this.dragListener = dragListener;
    }

    public interface OnDragListener {
        void onDrag(MotionEvent motionEvent);
    }
}
