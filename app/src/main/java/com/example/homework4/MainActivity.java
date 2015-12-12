package com.example.homework4;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.homework4.view.CompassView;

public final class MainActivity extends Activity {

    private SensorManager manager;
    private Sensor sensor;
    private CompassView compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        compass = (CompassView)findViewById(R.id.compass);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(compass,sensor,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(compass);
    }

    private static final String TAG = MainActivity.class.getSimpleName();
}
