package ru.ifmo.android_2015.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends Activity implements SensorEventListener {

    public static MainActivity CONTEXT;
    public static String PACKAGE_NAME;
    private MyRenderer renderer;

    private SensorManager sensorManager;
    private Sensor sensor;

    private MyGLSurfaceView mainSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        renderer = new MyRenderer(getString(R.string.vertex_shader), getString(R.string.fragment_shader));
        CONTEXT = this;
        PACKAGE_NAME = getPackageName();
        mainSurfaceView = new MyGLSurfaceView(this, renderer);

        mainSurfaceView.setEGLContextClientVersion(2);
        mainSurfaceView.setRenderer(renderer);
        setContentView(mainSurfaceView);
        mainSurfaceView.setAngle(90);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private float[] rotationMatrix = new float[16];
    private float[] orientation = new float[3];
    @Override
    public void onSensorChanged(SensorEvent event) {

        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        SensorManager.getOrientation(rotationMatrix, orientation);
        mainSurfaceView.setAngle(orientation[0] / (float) Math.PI * 180.0f);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
