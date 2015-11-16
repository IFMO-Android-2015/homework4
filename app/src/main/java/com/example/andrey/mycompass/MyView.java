package com.example.andrey.mycompass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by andrey on 16.11.15.
 */
public class MyView extends View implements SensorEventListener{

    private final Paint arrowToNorth = new Paint();
    private final Paint arrowToSouth = new Paint();
    private final Paint circle = new Paint();
    private final Paint text = new Paint();

    private float sin, cos;

    public MyView(Context context, AttributeSet attrs) {
        super(context,attrs);
        arrowToNorth.setColor(Color.BLUE);
        arrowToSouth.setColor(Color.RED);
        circle.setColor(Color.argb(122, 11, 102, 212));
        circle.setStrokeWidth(20);
        arrowToNorth.setStrokeWidth(15);
        arrowToSouth.setStrokeWidth(15);
        text.setColor(Color.BLACK);
        text.setTextSize(36);

        circle.setStyle(Paint.Style.STROKE);


    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float radius = Math.min(width,height) / 2.2f;
        int centerX = width / 2;
        int centerY = height / 2;

        canvas.drawCircle(centerX, centerY,radius , circle);
        radius -= 10;
        canvas.drawLine(centerX, centerY, centerX + radius * cos, centerY + radius * sin, arrowToNorth);
        canvas.drawLine(centerX,centerY, centerX + radius * (-cos), centerY + radius * (-sin), arrowToSouth);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float n = (float) Math.sqrt(event.values[0]*event.values[0]+event.values[1]*event.values[1]);
        sin = event.values[0] / n;
        cos = event.values[1] / n;
        invalidate();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
