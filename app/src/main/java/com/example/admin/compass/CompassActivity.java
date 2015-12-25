package com.example.admin.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class CompassActivity extends Activity implements SensorEventListener  {

    private ImageView compassImage;
    private float deg = 0f;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        compassImage = (ImageView) findViewById(R.id.CompassView);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float newDeg = Math.round(event.values[0]);
        RotateAnimation rotateAnimation = new RotateAnimation(
                deg,
                -newDeg,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setFillAfter(true);
        compassImage.startAnimation(rotateAnimation);
        deg = -newDeg;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}
