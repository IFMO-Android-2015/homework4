package ru.ifmo.android_2015.compass;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import static android.opengl.GLES20.*;

/**
 * Created by Owntage on 12/7/2015.
 */
public class MyRenderer implements GLSurfaceView.Renderer {

    float angle;

    String vertexShader;
    String fragmentShader;
    Texture arrowTexture;
    Shader shader;
    Quad arrowQuad;

    public MyRenderer(String vertexShader, String fragmentShader) {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {



        glClearColor(0.6f, 0.8f, 0.4f, 1.0f);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glEnable(GLES20.GL_TEXTURE);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        arrowTexture = new Texture("arrow");
        shader = new Shader(vertexShader, fragmentShader);
        arrowQuad = new Quad(shader, arrowTexture, 10f, 10f);

        Matrix.setIdentityM(shader.getProjection(), 0);
        Matrix.scaleM(shader.getProjection(), 0, 1.0f / (float) width * 128.0f, 1.0f / (float) height * 128.0f, 1.0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.setColor(1.0f, 0.9f, 1.0f, 1.0f);
        shader.updateCamera();
        shader.updateProjection();

        shader.bind();
        arrowQuad.setAngle(angle);
        arrowQuad.draw();
        
    }
}
