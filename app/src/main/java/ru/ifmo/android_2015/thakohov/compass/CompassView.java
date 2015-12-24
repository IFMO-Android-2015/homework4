package ru.ifmo.android_2015.thakohov.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ruslanthakohov on 23/12/15.
 */
public class CompassView extends View implements SensorEventListener {
    private Paint mNorthPaint;
    private Paint mSouthPaint;

    private float sinValue;
    private float cosValue;

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();

        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float centerX = width / 2.0f;
        float centerY = height / 2.0f;
        float radius = Math.min(width, height) * 0.7f;

        Log.d(TAG, "radius = " + String.valueOf(radius));
        //Log.d(TAG, "sin = " + String.valueOf(sinValue) + " cos = " + String.valueOf(cosValue));

        canvas.drawLine(centerX, centerY, centerX - sinValue * radius,
                centerY - cosValue * radius, mNorthPaint);
        canvas.drawLine(centerX, centerY, centerX + sinValue * radius,
                centerY + cosValue * radius, mSouthPaint);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float len = (float) Math.sqrt(event.values[0] * event.values[0] +
                event.values[1] * event.values[1]);
        sinValue = event.values[0] / len;
        cosValue = event.values[1] / len;

        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int ignored) {

    }

    private void init() {
        mNorthPaint = new Paint();
        mNorthPaint.setColor(getResources().getColor(android.R.color.holo_red_dark));
        mNorthPaint.setStrokeWidth(10f);

        mSouthPaint = new Paint();
        mSouthPaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        mSouthPaint.setStrokeWidth(10f);
    }

    private static final String TAG = CompassView.class.getSimpleName();
}
