package com.example.Compass;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MyActivity extends Activity {
    TextView text;
    ImageView compassImg;
    SensorManager sensorManager;
    Sensor compass;
    float data = 0;
    float rot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        rot = getRotateOrientation();
        text = (TextView)findViewById(R.id.textField);
        compassImg = (ImageView) findViewById(R.id.imageView);
        compassImg.setImageDrawable( getResources().getDrawable(R.drawable.img));
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        compass = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(compassListener,compass,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(compassListener,compass);
    }
    private float getRotateOrientation() {
        int rotate = getWindowManager().getDefaultDisplay().getRotation();
        switch (rotate) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return -90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 90;
            default: return 0;
        }
    }

    SensorEventListener compassListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float curr = -Float.parseFloat(String.valueOf(sensorEvent.values[0]));
            if (Math.abs(curr - data) >= 3) {
                Animation anim = new RotateAnimation(data + rot,curr + rot,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
                data = curr;
                text.setText(String.valueOf((int)-data) + " degrees" );
                anim.setFillAfter(true);
                anim.setDuration(1);
                compassImg.startAnimation(anim);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            if (i == SensorManager.SENSOR_STATUS_UNRELIABLE) {
                text.setText("Need calibration");
            }
        }
    };
}
