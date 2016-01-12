package ru.ifmo.android_2015.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Andreikapolin
 * @date 13.01.16
 */
public class CompassView extends View implements SensorEventListener {

    private final Paint circlePaint = new Paint();
    private final Paint linePaint = new Paint();

    private float lastX = 0;
    private float lastY = 0;

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        linePaint.setColor(0xFFFF9800);
        circlePaint.setStrokeWidth(10);
        linePaint.setStrokeWidth(10);
        circlePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        lastX = event.values[0];
        lastY = event.values[1];
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        double x0 = 1.0 * getWidth() / 2;
        double y0 = 1.0 * getHeight() / 2;
        double R = 1.0 * Math.min(getWidth(), getHeight()) / 2.5;
        canvas.drawCircle((float) x0, (float) y0, (float) R, circlePaint);

        double innerR = R * 0.99;
        double len = Math.sqrt(lastX * lastX + lastY * lastY);
        double cos = lastX / len;
        double sin = lastY / len;
        double x1 = x0 + innerR * cos;
        double y1 = y0 + innerR * sin;
        canvas.drawLine((float) x0, (float) y0, (float) x1, (float) y1, linePaint);
    }
}
