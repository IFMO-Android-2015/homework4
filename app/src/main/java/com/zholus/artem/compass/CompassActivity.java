package com.zholus.artem.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


public final class CompassActivity extends Activity implements SensorEventListener {

    private ArrowView arrow;
    private Sensor sensor;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        arrow = (ArrowView) findViewById(R.id.arrow);
        arrow.setAngle(0f);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        arrow.setAngle((float)Math.PI/2f - event.values[0] * (float)Math.PI / 180f);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}