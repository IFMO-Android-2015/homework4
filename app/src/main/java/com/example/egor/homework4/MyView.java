package com.example.egor.homework4;

/**
 * Created by egor on 23.12.15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;


public class MyView extends View implements SensorEventListener{

    private float sin, cos;
    private final Paint circle = new Paint();
    private final Paint arrowToNorth = new Paint();
    private final Paint arrowToSouth = new Paint();

    public MyView(Context context, AttributeSet attribute) {
        super(context, attribute);

        arrowToNorth.setColor(Color.BLACK);
        arrowToSouth.setColor(Color.RED);

        circle.setColor(Color.MAGENTA);
        circle.setStrokeWidth(30);

        arrowToNorth.setStrokeWidth(10);
        arrowToSouth.setStrokeWidth(30);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth(), height = getHeight();
        int centreX = width / 2, centreY = height / 2;
        float r = Math.min(width, height) / 2.5f;

        canvas.drawCircle(
                centreX,
                centreY,
                r,
                circle);

        r -= 15;

        canvas.drawLine(
                centreX,
                centreY,
                centreX + r * cos,
                centreY + r * sin,
                arrowToNorth);

        canvas.drawLine(
                centreX,
                centreY,
                centreX + r * (-cos),
                centreY + r * (-sin),
                arrowToSouth);
    }

    @Override
    public void onSensorChanged(SensorEvent isEvent) {
        float n = (float) Math.sqrt(isEvent.values[0] * isEvent.values[0] + isEvent.values[1] * isEvent.values[1]);
        sin = isEvent.values[0] / n;
        cos = isEvent.values[1] / n;
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
