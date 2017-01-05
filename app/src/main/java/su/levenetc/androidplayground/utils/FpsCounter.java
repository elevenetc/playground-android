package su.levenetc.androidplayground.utils;

/**
 * Created by eugene.levenetc on 05/01/2017.
 */
public class FpsCounter {

	private long startTime = System.nanoTime();
	public int fps = 0;
	private Handler handler;

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public void onDrawFrame() {
		fps++;
		if (System.nanoTime() - startTime >= 1000000000) {
			handler.onDrawFrame(fps);
			fps = 0;
			startTime = System.nanoTime();
		}
	}

	public interface Handler {
		void onDrawFrame(int fps);
	}
}
