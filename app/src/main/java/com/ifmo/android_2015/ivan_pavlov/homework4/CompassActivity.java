package com.ifmo.android_2015.ivan_pavlov.homework4;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ifmo.android_2015.ivan_pavlov.homework4.views.CompassView;

public class CompassActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor magnetic_field;
    private Sensor accelerometer;
    private CompassView compassView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        compassView = (CompassView)findViewById(R.id.compass);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic_field = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(compassView, magnetic_field, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(compassView, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(compassView);
    }
}
