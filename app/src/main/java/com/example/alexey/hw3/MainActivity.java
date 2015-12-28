package com.example.alexey.hw3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private float deg = 0f;
    private Sensor compassSensor;
    private ImageView compassImage;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compassImage = (ImageView) findViewById(R.id.compas);
        textView = (TextView) findViewById(R.id.text);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        compassSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void onSensorChanged(SensorEvent event) {
        float newDeg = Math.round(event.values[0]);
        textView.setText(newDeg + "°");
        RotateAnimation rotateAnimation = new RotateAnimation(deg, -newDeg, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setFillAfter(true);
        compassImage.startAnimation(rotateAnimation);
        deg = -newDeg;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}
