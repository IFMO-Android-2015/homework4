package com.ifmo.android_2015.ivan_pavlov.homework4.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View implements SensorEventListener {

    private Float azimut = (float)0;

    private final Paint paintCircle = new Paint();
    private final Paint RedPaint = new Paint();
    private final Paint BluePaint = new Paint();

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        RedPaint.setColor(Color.RED);
        RedPaint.setStrokeWidth(10);

        BluePaint.setColor(Color.BLUE);
        BluePaint.setStrokeWidth(10);

        paintCircle.setColor(Color.BLACK);
        paintCircle.setStrokeWidth(10);
        paintCircle.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();
        float centerX = width / 2;
        float centerY = height / 2;
        float radius = Math.min(width, height) / 2 * (float) 0.9;
        canvas.drawCircle(centerX, centerY, radius, paintCircle);
        canvas.rotate(-azimut, centerX, centerY);
        canvas.drawLine(centerX, centerY, centerX, centerY+radius, RedPaint);
        canvas.drawLine(centerX, centerY, centerX, centerY-radius, BluePaint);
    }

    private float [] accelerometerData;
    private float [] magneticFieldData;
    private float [] r = new float[9];
    private float [] result = new float[3];

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerData = sensorEvent.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magneticFieldData = sensorEvent.values;
                break;
        }
        if(accelerometerData != null && magneticFieldData != null) {
            SensorManager.getRotationMatrix(r, null, accelerometerData, magneticFieldData);
            SensorManager.getOrientation(r, result);
            azimut = (float)Math.toDegrees(result[0]);
            accelerometerData = null;
            magneticFieldData = null;
            this.invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}
