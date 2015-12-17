package com.example.cawa.compas;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SensorManager Sm;
    Sensor sensor;
    CompasView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (CompasView) findViewById(R.id.compas);

        Sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = (Sensor) Sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected  void onResume() {
        super.onResume();

        Sm.registerListener(view, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Sm.unregisterListener(view);
    }
}
