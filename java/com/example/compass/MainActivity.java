package com.example.compass;

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

    ImageView imageView;

    float[] RMatrix;
    float[] IMatrix;
    float[] magnetData;
    float[] accData;
    float[] orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RMatrix = new float[9];
        IMatrix = new float[9];
        orientation = new float[3];

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.compass);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accData = event.values.clone();
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magnetData = event.values.clone();
        }
        if (accData != null && magnetData != null){

            SensorManager.getRotationMatrix(RMatrix, IMatrix, accData, magnetData);
            SensorManager.getOrientation(RMatrix, orientation);

            imageView.setRotation((540 - (orientation[0] + 3.14f) / 6.28f * 360f) % 360);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
