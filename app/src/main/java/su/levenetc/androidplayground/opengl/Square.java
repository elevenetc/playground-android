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
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.utils.GLUtils;

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

	// number of coordinates per vertex in this array
	static final int COORDS_PER_VERTEX = 3;
	static float squareCoords[] = {
			-0.5f, 0.5f, 0.0f,   // top left
			-0.5f, -0.5f, 0.0f,   // bottom left
			0.5f, -0.5f, 0.0f,   // bottom right
			0.5f, 0.5f, 0.0f}; // top right

	private final short drawOrder[] = {0, 1, 2, 0, 2, 3};

	private final int vertexStride = COORDS_PER_VERTEX * 4;

	private final float color[] = {1.0f, 0.709803922f, 0.898039216f, 1.0f};

	public Square(Context context) {
		// initialize vertex byte buffer for shape coordinates
		ByteBuffer bb = ByteBuffer.allocateDirect(
				// (# of coordinate values * 4 bytes per float)
				squareCoords.length * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(squareCoords);
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

		// get handle to shape's transformation matrix
		int mMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
		GLUtils.checkGlError("glGetUniformLocation");

		// Apply the projection and view transformation
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
		GLUtils.checkGlError("glUniformMatrix4fv");

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


}