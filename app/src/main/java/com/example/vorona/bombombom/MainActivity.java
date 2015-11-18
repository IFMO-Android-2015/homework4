package com.example.vorona.bombombom;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity implements SensorEventListener {
    private TextView textView;
    private Sensor sensor;
    private SensorManager sensorManager;
    AngleView angle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        angle1 = (AngleView)findViewById(R.id.angle_1);
        textView = (TextView)findViewById(R.id.txt);
        Random random = new Random();
        angle1.setAngle((float)(random.nextFloat() * Math.PI * 2));

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textView.setText(Float.toString(event.values[0]));
        angle1.setAngle((float) (-event.values[0]/360 * 2 * Math.PI) * 10 / 10);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
