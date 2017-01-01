package su.levenetc.androidplayground.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class GLRenderer implements GLSurfaceView.Renderer {

//	private Square[] squares;

    // mvpMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mvpMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] rotationMatrix = new float[16];

    private float angle;
    private Context context;
    private float camLocX = 0.0f;
    private float camLocY = 0.0f;
    private float camLocZ = 3.0f;
    private float camTargetLocX = 0.0f;
    private float camTargetLocY = 0.0f;
    private float camTargetLocZ = 0.0f;
    private float upX = 0.0f;
    private float upY = 1.0f;
    private float upZ = 0.0f;

    private SquareMatrix squareMatrix;
    private float camY;
    private float camX;
    private float xVal;
    private float yVal;

    public GLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        squareMatrix = new SquareMatrix(1, 1, context);
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0,
                camLocX, camLocY, camLocZ,//camera location
                camTargetLocX, camTargetLocY, camTargetLocZ,//target location
                upX, upY, upZ);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        // Draw squares
        squareMatrix.draw(mvpMatrix);
//		for (Square square : squares) square.draw(mvpMatrix);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    public void setTestValues(float xVal, float yVal) {
        this.xVal = xVal;
        this.yVal = yVal;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void updateCameraLocation(float camX, float camY) {
        this.camX = camX;
        this.camY = camY;
    }

    public void setCamTargetLocZ(float camTargetLocZ) {
        this.camTargetLocZ = camTargetLocZ;
    }

    public void setCamLocX(float camLocX) {
        this.camLocX = camLocX;
    }

    public void setCamLocY(float camLocY) {
        this.camLocY = camLocY;
    }

    public void setCamLocZ(float camLocZ) {
        this.camLocZ = camLocZ;
    }

    public void setCamTargetLocX(float camTargetLocX) {
        this.camTargetLocX = camTargetLocX;
    }

    public void setCamTargetLocY(float camTargetLocY) {
        this.camTargetLocY = camTargetLocY;
    }

    public void setUpZ(float upZ) {
        this.upZ = upZ;
    }

    public void setUpY(float upY) {
        this.upY = upY;
    }

    public void setUpX(float upX) {
        this.upX = upX;
    }
}