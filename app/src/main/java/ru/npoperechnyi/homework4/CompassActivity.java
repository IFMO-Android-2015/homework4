package ru.npoperechnyi.homework4;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magneticField;
    private Sensor accelerometer;

    private float[] valuesAccelerometer;
    private float[] valuesMagnetic;

    private float[] Rot;
    private float[] Inc;
    private float[] values;

    CompassView compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        valuesAccelerometer = new float[3];
        valuesMagnetic = new float[3];
        Rot = new float[9];
        Inc = new float[9];
        values = new float[3];
        compass = (CompassView) findViewById(R.id.compass);
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, magneticField);
        sensorManager.unregisterListener(this, accelerometer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD: {
                for (int i = 0; i < 3; ++i) {
                    valuesMagnetic[i] = event.values[i];
                }
                break;
            }
            case Sensor.TYPE_ACCELEROMETER: {
                for (int i = 0; i < 3; ++i) {
                    valuesAccelerometer[i] = event.values[i];
                }
                break;
            }
        }

        if (SensorManager.getRotationMatrix(Rot, Inc,
                valuesAccelerometer,
                valuesMagnetic)) {
            SensorManager.getOrientation(Rot, values);
            compass.update(values[0]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
}
