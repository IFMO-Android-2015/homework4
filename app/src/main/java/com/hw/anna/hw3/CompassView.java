package com.hw.anna.hw3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View implements SensorEventListener {

    private float x = 0, y = 0;

    private final Paint NorthArrow = new Paint();

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        NorthArrow.setColor(Color.BLUE);
        NorthArrow.setStrokeWidth(15);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float len = Math.min(width, height) / 2.3f;
        double abs = Math.sqrt(x * x + y * y);

        float x_center = width / 2;
        float y_center = height / 2;

        double x2 = x_center + len * x / abs;
        double y2 = y_center + len * y / abs;

        canvas.drawLine(x_center, y_center, (float)(x2), (float)(y2), NorthArrow);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];

        invalidate();
    }

}