package com.gaudima.gaudima.compass;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private CompassView compassView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        compassView = (CompassView)findViewById(R.id.compassView);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        compassView.setDebugTextView((TextView)findViewById(R.id.debugTextView));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(compassView, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(compassView);
    }
}
