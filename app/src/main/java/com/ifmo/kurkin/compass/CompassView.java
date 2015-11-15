package com.ifmo.kurkin.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kurkin on 15.11.15.
 */
public class CompassView extends View implements SensorEventListener {

    private final Paint linePaint = new Paint();
    private final Paint cyclePaint = new Paint();

    private float angle;

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(10);

        cyclePaint.setColor(Color.BLACK);
        cyclePaint.setStrokeWidth(10);
        cyclePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int r = Math.min(width, height) / 3;

        float x = (float) (r * Math.sin(angle));
        float y = (float) (r * Math.cos(angle));

        canvas.drawLine(width / 2, height / 2, width / 2 - x, height / 2 - y, linePaint);
        canvas.drawCircle(width / 2, height / 2, r, cyclePaint);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        angle = (float) (event.values[0] / 180 * Math.PI);

        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
