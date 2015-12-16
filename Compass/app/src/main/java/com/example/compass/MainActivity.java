package com.example.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private ImageView imageView;
    private SensorManager mSensorManager;
    private Sensor mOrientation;
    private TextView textView;
    private float degree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(formatData(new float[]{0, 0, 0}));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        textView.setText(formatData(event.values));
        float newDegree = event.values[0]; // azimuth

        // fix for correct passing North way
        float rotate1 = 0f;
        float rotate2 = 0f;
        if (Math.abs(degree - newDegree) > 180) {
            if (degree <= 180 && newDegree > 180) {
                rotate1 = 360;
            } else if (newDegree <= 180 && degree > 180) {
                rotate2 = 360;
            }
        }

        RotateAnimation rotateAnimation = new RotateAnimation(-rotate1-degree, -rotate2-newDegree,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.reset();
        rotateAnimation.setDuration(400);
        rotateAnimation.setFillAfter(true);
        imageView.startAnimation(rotateAnimation);
        degree = newDegree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private String formatData(float[] data) {
        return String.format("Azimuth: %f\nPitch: %f\nRoll: %f", data[0], data[1], data[2]);
    }
}
