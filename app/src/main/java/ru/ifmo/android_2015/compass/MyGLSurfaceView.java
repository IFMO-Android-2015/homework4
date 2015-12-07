package ru.ifmo.android_2015.compass;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Owntage on 12/7/2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    MyRenderer myRenderer;


    public MyGLSurfaceView(Context context, MyRenderer myRenderer) {
        super(context);
        this.myRenderer = myRenderer;
    }

    public void setAngle(float angle) {
        class MyRunnable implements Runnable {
            float angle;

            public MyRunnable(float angle) {
                this.angle = angle;
            }

            public void run() {
                myRenderer.setAngle(angle);
            }
        }

        queueEvent(new MyRunnable(angle));
    }
}
