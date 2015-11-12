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
 * Created by sandwwraith(@gmail.com)
 * ITMO University, 2015.
 */
public class CompassView extends View implements SensorEventListener {

    private final Paint circlePaint = new Paint();
    private final Paint arrowPaint = new Paint();

    private float x_mag = 0, y_mag = 0; //Data from sensor

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        circlePaint.setColor(new Random().nextInt());
        int c = circlePaint.getColor();
        c |= (0xFF << 16); //Setting red value to maximum
        arrowPaint.setColor(c);

        circlePaint.setStrokeWidth(15);
        arrowPaint.setStrokeWidth(10);

        circlePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x_mag = event.values[0];
        y_mag = event.values[1];
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        double len = Math.sqrt(x_mag * x_mag + y_mag * y_mag);
        double cos = x_mag / len;
        double sin = y_mag / len;

        float R; // Circle radius
        int width = getWidth();
        int height = getHeight();

        float x_center = width / 2;
        float y_center = height / 2;
        R = Math.min(width, height) / 2.1f; // Cannot use 2 because android cannot stroke line inside - half of the line won't fit screen

        canvas.drawCircle(x_center, y_center, R, circlePaint);

        double x_coord = x_center + R * cos;
        double y_coord = y_center + R * sin;
        canvas.drawLine(x_center, y_center, (float) x_coord, (float) y_coord, arrowPaint);
    }
}
