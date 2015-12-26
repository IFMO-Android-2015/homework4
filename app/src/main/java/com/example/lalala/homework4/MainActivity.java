package com.example.lalala.homework4;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView2);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        compass = new Compass();
        sensorManager.registerListener(compass, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(compass, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(compass);
    }

    ImageView imageView;
    SensorManager sensorManager;
    Sensor sensor;
    Compass compass;

    private class Compass implements SensorEventListener {
        private float angle = 0.0f;
        private float nangle = 0.0f;
        private int cnt = 0;
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
//            nangle = (float)Math.atan2(y, x);
            nangle = (nangle * cnt + x) / ++cnt;
            if (cnt == 10) {
                RotateAnimation ra = new RotateAnimation(angle, -nangle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ra.setFillAfter(true);
                ra.setDuration(100);
                imageView.startAnimation(ra);
                angle = -nangle;
                nangle = 0.0f;
                cnt = 0;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
