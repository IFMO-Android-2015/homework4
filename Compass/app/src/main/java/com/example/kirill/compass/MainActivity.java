package com.example.kirill.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private ImageView image;
    private SensorManager sensorManager;
    private Sensor sensorAcc;
    private Sensor sensorMagn;
    private float[] accData;
    private float[] magnData;
    private boolean accDataSet;
    private boolean magnDataSet;
    private float[] rotMatrix;
    private float[] orientData;
    private float rotDeg = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagn = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        image = (ImageView) findViewById(R.id.CompassView);

        accData = new float[3];
        magnData = new float[3];
        rotMatrix = new float[9];
        orientData = new float[3];

        accDataSet = false;
        magnDataSet = false;
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorAcc, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorMagn, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, sensorAcc);
        sensorManager.unregisterListener(this, sensorMagn);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == sensorAcc) {
            System.arraycopy(event.values, 0, accData, 0, event.values.length);
            accDataSet = true;
        } else if (event.sensor == sensorMagn) {
            System.arraycopy(event.values, 0, magnData, 0, event.values.length);
            magnDataSet = true;
        }
        if (accDataSet && magnDataSet) {
            SensorManager.getRotationMatrix(rotMatrix, null, accData, magnData);
            SensorManager.getOrientation(rotMatrix, orientData);
            float azimuth = (float)(Math.toDegrees(orientData[0]) + 360) % 360;
            RotateAnimation rotAnimation = new RotateAnimation(
                    rotDeg,
                    -azimuth,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            rotAnimation.setDuration(100);

            rotAnimation.setFillAfter(true);

            image.startAnimation(rotAnimation);
            rotDeg = -azimuth;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
