package com.vantuz.compass.app;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements SensorEventListener {
    SensorManager manager;
    private Sensor sensor;
    private CompassView compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (sensor != null){
            setContentView(R.layout.layout_sensor);
            compass = (CompassView) findViewById(R.id.compass);
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        }
        else {
            setContentView(R.layout.layout_ochen_zhal);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        compass.setValues(event.values[0], event.values[1]);
        compass.redraw();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
