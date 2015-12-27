package com.github.timchenko.compas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor sensor;
    private SensorManager manager;
    private float kx, ky;
    MyView compassView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compassView = new MyView(this);
        setContentView(compassView);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (savedInstanceState == null) {
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float angle = (float) Math.atan(y/x);
        angle += (x < 0 ? 0 : Math.PI);
        kx = (float) Math.sin(angle);
        ky = (float) Math.cos(angle);
        compassView.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {    }

    class MyView extends View {
        private Paint paintNorth = new Paint();
        private Paint paintCircle = new Paint();

        public MyView(Context context) {
            super(context);
            paintNorth.setColor(0xFF3F51B5);
            paintNorth.setStrokeWidth(16);

            paintCircle.setColor(0xFFCC0000);
            paintCircle.setStrokeWidth(16);
            paintCircle.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Point center = new Point(canvas.getWidth() / 2, canvas.getHeight() / 2);
            float radius = Math.min(canvas.getHeight(), canvas.getWidth()) / 3;
            canvas.drawColor(Color.WHITE);
            canvas.drawCircle(center.x, center.y, radius, paintCircle);
            canvas.drawLine(center.x, center.y, center.x + radius * kx * .9f, center.y + radius * ky * .9f, paintNorth);
        }

    }
}