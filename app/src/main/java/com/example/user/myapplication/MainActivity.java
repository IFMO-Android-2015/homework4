package com.example.user.myapplication;

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

import static android.hardware.Sensor.TYPE_ORIENTATION;

public class MainActivity extends Activity implements SensorEventListener {
    TextView text;
    ImageView img;
    SensorManager sensorManager;
    Sensor compass;
    float data = 0;
    float rotate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        compass = sensorManager.getDefaultSensor(TYPE_ORIENTATION);
        rotate = getRotate();
        text = (TextView) findViewById(R.id.textView);
        img = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, compass);
    }

    private float getRotate() {
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
            default:
                return 0;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float current = -Float.parseFloat(String.valueOf(sensorEvent.values[0]));
        if (Math.abs(current - data) >= 3) {
            Animation anim = new RotateAnimation(data + rotate, current + rotate, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            data = current;
            text.setText(String.valueOf((int) -data) + " degrees");
            anim.setFillAfter(true);
            anim.setDuration(1);
            img.startAnimation(anim);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



}