package su.levenetc.androidplayground.opengl;

import android.content.Context;
import android.opengl.GLES20;
import su.levenetc.androidplayground.utils.GLUtils;

/**
 * Created by eugene.levenetc on 25/12/2016.
 */
public class FragmentShader {
	private final int id;

	public FragmentShader(Context context, int resourceId) {
		final String source = GLUtils.readTextFileFromResource(context, resourceId);
		id = GLUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, source);
	}

	public int getId() {
		return id;
	}
}
