package com.example.studenikina.hw4;

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
 * Created by Лиза on 22.11.2015.
 */

public class NewView extends View implements SensorEventListener {

    private float x, y; // mass event
    private final Paint circle = new Paint();
    private final Paint arrow = new Paint();


    public NewView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        circle.setColor(Color.YELLOW);
        arrow.setColor(Color.BLUE);
        circle.setStrokeWidth(20);
        arrow.setStrokeWidth(10);

        circle.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        double len = Math.sqrt(x * x + y * y);

        double radius = Math.min(width,height) / 2.2f;
        int centerX = width / 2;
        int centerY = height / 2;

        double cos = x / len;
        double sin = y / len;

        canvas.drawCircle(centerX, centerY, (float) radius , circle);

        double x_coord = centerX + radius * cos;
        double y_coord = centerY + radius * sin;
        canvas.drawLine(centerX, centerY, (float) x_coord, (float) y_coord, arrow);
        invalidate();
    }
    //end
}
