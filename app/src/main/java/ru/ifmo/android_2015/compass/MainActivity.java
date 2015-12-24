package ru.ifmo.android_2015.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor magnetometer;
    Sensor accelerometer;
    ImageView myImageView;
    float[] rotationMatrix;
    float[] inclinationMatrix;
    float[] geomagnetic;
    float[] gravity;
    float[] orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rotationMatrix = new float[9];
        inclinationMatrix = new float[9];
        orientation = new float[3];
        myImageView = (ImageView) findViewById(R.id.myImageView);
        myImageView.setImageResource(R.drawable.compass);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values.clone();
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values.clone();
        }
        if (gravity != null && geomagnetic != null) {
            SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, gravity, geomagnetic);
            SensorManager.getOrientation(rotationMatrix, orientation);
            myImageView.setRotation((- orientation[0] / 3.14f * 180f + 360) % 360);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}