package su.levenetc.androidplayground.utils;

import android.opengl.Matrix;

/**
 * Created by eugene.levenetc on 27/12/2016.
 */
public class Mat {

	private float x;
	private float y;
	private float z;
	private float[] mat = new float[16];

	public Mat() {
		Matrix.setIdentityM(mat, 0);
	}

	public Mat(float x, float y, float z) {
		this();
		setX(x);
		setY(y);
		setZ(z);
	}

	public float[] getMat() {
		return mat;
	}

	public void setX(float x) {
		this.x = x;
		mat[3] = x;
	}

	public void setY(float y) {
		this.y = y;
		mat[7] = y;
	}

	public void setZ(float z) {
		this.z = z;
		mat[11] = z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}


}
