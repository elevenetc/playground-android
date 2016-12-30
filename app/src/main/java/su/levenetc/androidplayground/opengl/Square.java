/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package su.levenetc.androidplayground.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * A two-dimensional square for use as a drawn object in OpenGL ES 2.0.
 */
public class Square {

	private final FloatBuffer vertexBuffer;
	private final ShortBuffer drawListBuffer;
	private final GLProgram glProgram;
	private int positionLoc;

	private static final int BYTES = 2;

	static final int COORDS_PER_VERTEX = 3;
	private final float squareCoordinates[];

	private final short drawOrder[] = {0, 1, 2, 0, 2, 3};

	private final int vertexStride = COORDS_PER_VERTEX * 4;

	private float yTranslation;
	private float xTranslation;
	private final float[] color;

	public Square(
			float width,
			float height,
			float xTranslation,
			float yTranslation,
			int color,
			Context context) {
		this.xTranslation = xTranslation;
		this.yTranslation = yTranslation;
		this.color = Utils.intToFloatRgba(color, 1);

		squareCoordinates = initVertices(width, height);

		ByteBuffer bb = ByteBuffer.allocateDirect(squareCoordinates.length * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(squareCoordinates);
		vertexBuffer.position(0);

		// initialize byte buffer for the draw list
		ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * BYTES);
		dlb.order(ByteOrder.nativeOrder());
		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(drawOrder);
		drawListBuffer.position(0);

		final VertexShader vertexShader = new VertexShader(context, R.raw.simple_vertex_shader);
		final FragmentShader fragmentShader = new FragmentShader(context, R.raw.simple_fragment_shader);

		glProgram = new GLProgram(vertexShader, fragmentShader);
	}

	public void draw(float[] mvpMatrix) {

		final int program = glProgram.getId();

		GLES20.glUseProgram(program);

		setPosition(program);
		setColor(program);

		Matrix.translateM(mvpMatrix, 0, xTranslation, yTranslation, 0);

		// Apply the projection and view transformation
		GLES20.glUniformMatrix4fv(
				GLES20.glGetUniformLocation(program, "uMVPMatrix"),
				1,
				false,
				mvpMatrix,
				0
		);

		drawElements();

		// Disable vertex array
		GLES20.glDisableVertexAttribArray(positionLoc);
	}

	private void setPosition(int program) {
		// get handle to vertex shader's vPosition member
		positionLoc = GLES20.glGetAttribLocation(program, "vPosition");

		// Enable a handle to the triangle vertices
		GLES20.glEnableVertexAttribArray(positionLoc);

		// Prepare the triangle coordinate data
		GLES20.glVertexAttribPointer(
				positionLoc, COORDS_PER_VERTEX,
				GLES20.GL_FLOAT, false,
				vertexStride, vertexBuffer);
	}

	private void setColor(int program) {
		int colorLoc = GLES20.glGetUniformLocation(program, "u_Color");
		GLES20.glUniform4fv(colorLoc, 1, color, 0);
	}

	private void drawElements() {
		GLES20.glDrawElements(
				GLES20.GL_TRIANGLES,
				drawOrder.length,
				GLES20.GL_UNSIGNED_SHORT,
				drawListBuffer
		);
	}

	private float[] initVertices(float width, float height) {

		return new float[]{
				-width / 2, height / 2, 0.0f,   // top left
				-width / 2, -height / 2, 0.0f,   // bottom left
				width / 2, -height / 2, 0.0f,   // bottom right
				width / 2, height / 2, 0.0f}; //top right
	}

}