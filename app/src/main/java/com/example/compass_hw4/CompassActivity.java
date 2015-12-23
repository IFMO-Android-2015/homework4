package com.example.compass_hw4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    public class CompView extends View {
        Paint paint;

        public CompView(Context context) {
            super(context);
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setAntiAlias(true);
        }

        protected void onDraw(Canvas canvas) {
            int h = getHeight();
            int x = getWidth()/2;
            int y = h/2;

            if (rotation != null)
                canvas.rotate(-rotation * 57.32f, x, y); // 360/2pi = 57.32

            paint.setColor(Color.BLUE);
            canvas.drawLine(x, y, x, h/4, paint);
            paint.setColor(Color.RED);
            canvas.drawLine(x, h * 3/4, x, y, paint);
        }
    }

    private CompView compassView;

    private SensorManager sensorManager;

    private Sensor mSensor, aSensor;

    private float[] mSensorValues, aSensorValues;

    Float rotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        compassView = new CompView(this);
        setContentView(compassView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        rotation = 0f;
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            aSensorValues = event.values;

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mSensorValues = event.values;

        if(aSensorValues != null && mSensorValues != null) {
            float[] R = new float[9], L = new float[9];

            if(SensorManager.getRotationMatrix(R, L, aSensorValues, mSensorValues)) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                rotation = orientation[0];
                compassView.invalidate();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
