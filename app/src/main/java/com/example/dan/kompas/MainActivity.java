package com.example.dan.kompas;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private KompasView kompasView;
    private Display display;
    private SensorManager sensorManager;
    private Sensor rotationSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kompasView = (KompasView) findViewById(R.id.kompasView);
        display = getWindowManager().getDefaultDisplay();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Not deprecated
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private double displayRotation;
    private float[] rotationMatrix = new float[16];
    private float[] orientation = new float[3];
    @Override
    public void onSensorChanged(SensorEvent event) {
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        SensorManager.getOrientation(rotationMatrix, orientation);
        int rotation = display.getRotation();
        if (rotation == Surface.ROTATION_0) {
            displayRotation = 0;
        } else if (rotation == Surface.ROTATION_90) {
            displayRotation = Math.PI/2;
        } else if (rotation == Surface.ROTATION_180) {
            displayRotation = Math.PI;
        } else if (rotation == Surface.ROTATION_270) {
            displayRotation = Math.PI * 3 / 2;
        }
        kompasView.setAngle(Math.PI / 2 + orientation[0] + displayRotation);
    }

    @Override
     public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
