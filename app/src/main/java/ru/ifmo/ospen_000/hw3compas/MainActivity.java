package ru.ifmo.ospen_000.hw3compas;

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

    private ImageView pointer;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] lastAccelerometers = new float[3];
    private float[] lastMagnetometers = new float[3];
    private boolean haveLastAccelerometer = false;
    private boolean haveLastMagnetometer = false;
    private float[] rotation = new float[9];
    private float[] orientation = new float[3];
    private float degree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        pointer = (ImageView) findViewById(R.id.pointer);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {
            System.arraycopy(event.values, 0, lastAccelerometers, 0, event.values.length);
            haveLastAccelerometer = true;
        } else if (event.sensor == magnetometer) {
            System.arraycopy(event.values, 0, lastMagnetometers, 0, event.values.length);
            haveLastMagnetometer = true;
        }
        if (haveLastAccelerometer && haveLastMagnetometer) {
            SensorManager.getRotationMatrix(rotation, null, lastAccelerometers, lastMagnetometers);
            SensorManager.getOrientation(rotation, orientation);
            float azimuthInRadians = orientation[0];
            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;
            RotateAnimation ra = new RotateAnimation(
                    degree,
                    -azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            ra.setDuration(200);

            ra.setFillAfter(true);

            pointer.startAnimation(ra);
            degree = -azimuthInDegress;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

}