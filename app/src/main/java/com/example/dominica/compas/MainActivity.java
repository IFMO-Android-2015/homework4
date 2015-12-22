package com.example.dominica.compas;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


public class MainActivity extends Activity implements SensorEventListener {
    private Sensor sensor;
    private SensorManager sensorManager;
    AngleView angle1;
    AngleView angle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compas_main);

        angle1 = (AngleView)findViewById(R.id.nouth);
        angle2 = (AngleView)findViewById(R.id.south);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        angle1.setAngle((float) (Math.PI * event.values[0]  / 180));
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

