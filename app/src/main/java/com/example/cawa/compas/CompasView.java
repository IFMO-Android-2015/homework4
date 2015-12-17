package com.example.cawa.compas;

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
 * Created by Cawa on 30.11.2015.
 */
public class CompasView extends View implements SensorEventListener {
    double sin = 0;
    double cos = 0;
    //double eps = 0.01;
    Paint paintNorth =  new Paint();
    Paint paintSouth =  new Paint();

    public CompasView(Context context, AttributeSet attributes) {
        super(context, attributes);

        paintNorth.setColor(Color.BLUE);
        paintSouth.setColor(Color.RED);
        paintNorth.setStrokeWidth(10);
        paintSouth.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int minSize = Math.min(width, height) / 2;
        int centerX = width / 2;
        int centerY = height / 2;

        canvas.drawLine(centerX, centerY, (float)(centerX + minSize * cos),
                (float)(centerY + minSize * sin), paintNorth);
        canvas.drawLine(centerX, centerY, (float)(centerX - minSize * cos),
                (float)(centerY - minSize * sin), paintSouth);

        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double c = Math.sqrt(event.values[0] * event.values[0] + event.values[1]*event.values[1]);
        sin = event.values[0] / c;
        cos = event.values[1] / c;
    }
}
