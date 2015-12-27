package ru.ifmo.android_2015.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * @author creed
 * @date 27.12.15
 */
public class CompassView extends View implements SensorEventListener{
    private final Paint circlePaint = new Paint();
    private final Paint arrowPaint = new Paint();

    private float x = 0;
    private float y = 0;

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        int c = new Random().nextInt() | 0xFF0000;
        arrowPaint.setColor(c);
        circlePaint.setStrokeWidth(15);
        arrowPaint.setStrokeWidth(10);

        circlePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        double x0 = getWidth() / 2;
        double y0 = getHeight() / 2;
        double R = Math.min(getWidth(), getHeight()) / 2.15f;

        canvas.drawCircle((float) x0, (float) y0, (float) R, circlePaint);

        double len = Math.sqrt(x * x + y * y);
        double cos = x / len;
        double sin = y / len;
        double x1 = x0 + R * cos;
        double y1 = y0 + R * sin;
        canvas.drawLine((float) x0, (float) y0, (float) x1, (float) y1, arrowPaint);
    }
}
