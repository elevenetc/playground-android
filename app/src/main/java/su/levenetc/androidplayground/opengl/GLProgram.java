package su.levenetc.androidplayground.opengl;

import android.content.Context;
import android.opengl.GLES20;
import su.levenetc.androidplayground.R;

/**
 * Created by eugene.levenetc on 25/12/2016.
 */
public class GLProgram {

	private final int id;

	public GLProgram(VertexShader vertexShader, FragmentShader fragmentShader) {
		id = GLES20.glCreateProgram();
		GLES20.glAttachShader(id, vertexShader.getId());
		GLES20.glAttachShader(id, fragmentShader.getId());
		GLES20.glLinkProgram(id);
	}

	public int getId() {
		return id;
	}
}
