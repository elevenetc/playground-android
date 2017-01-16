package su.levenetc.androidplayground.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eugene.levenetc on 16/01/2017.
 */
public class MapEditorLayer extends View {

	private Map<LatLng, Point> points = new HashMap<>();
	private Paint paint = new Paint();

	public MapEditorLayer(Context context) {
		super(context);
		init();
	}

	public MapEditorLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.FILL);
	}

	public void updateProjection(Projection projection) {
		for (Map.Entry<LatLng, Point> entry : points.entrySet()) {
			final LatLng latLng = entry.getKey();
			final Point point = entry.getValue();

			final Point result = projection.toScreenLocation(latLng);
			point.set(result.x, result.y);
		}
		invalidate();
	}

	public void addPoint(LatLng latLng) {
		points.put(latLng, new Point());
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);


		for (Map.Entry<LatLng, Point> entry : points.entrySet()) {
			final Point point = entry.getValue();
			canvas.drawCircle(point.x, point.y, 100, paint);
		}
	}
}
