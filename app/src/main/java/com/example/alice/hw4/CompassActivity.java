package com.example.alice.hw4;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private String TAG = "my tag";
    SensorManager sensorManager;
    Sensor magnetField;
    Sensor accelerometer;
    float[] accelerometerData;
    float[] magnetFieldData;
    float[] rotationMatrix;
    float[] orientationData;
    CompassView compassView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        magnetField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        compassView = (CompassView) findViewById(R.id.compass);

    }

    public void onSensorChanged(SensorEvent event) {
        rotationMatrix = new float[9];
        orientationData = new float[3];
        
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerData = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetFieldData = event.values.clone();
                break;
        }
        if (magnetFieldData != null && accelerometerData != null) {
            SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magnetFieldData);
            SensorManager.getOrientation(rotationMatrix, orientationData);
            compassView.setAngle((float) (Math.toDegrees(orientationData[0])));
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, sensor.toString() + " " + accuracy);

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetField, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
