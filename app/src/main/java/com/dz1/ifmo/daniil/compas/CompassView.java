package com.dz1.ifmo.daniil.compas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by daniil on 18.11.15.
 */
public class CompassView extends View implements SensorEventListener {
    private Paint redArrowPaint = new Paint();
    private Paint blueArrowPaint = new Paint();
    private Paint circlePaint = new Paint();

    private float x, y;

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        redArrowPaint.setColor(Color.RED);
        redArrowPaint.setStrokeWidth(10);

        blueArrowPaint.setColor(Color.BLUE);
        blueArrowPaint.setStrokeWidth(10);

        circlePaint.setColor(Color.GRAY);
        circlePaint.setStrokeWidth(20);
        circlePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        float startX = width / 2;
        float startY = height  / 2;

        double sq = Math.sqrt(x * x + y * y);
        double length = Math.min(width, height) * 0.45f;

        float stopX = (float) (startX - length * x / sq);
        float stopY = (float) (startY - length * y / sq);
        canvas.drawLine(startX, startY, stopX, stopY, redArrowPaint);

        stopX = (float) (startX + length * x / sq);
        stopY = (float) (startY + length * y / sq);
        canvas.drawLine(startX, startY, stopX, stopY, blueArrowPaint);

        canvas.drawCircle(startX, startY, (float) length, circlePaint);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
