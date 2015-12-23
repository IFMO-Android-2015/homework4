package com.example.pinkdonut.mycompass;

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
 * Created by pinkdonut on 22.12.2015.
 */
public class Compass extends View implements SensorEventListener {
    private float x = 0, y = 0;
    private Paint NorthArrow = new Paint();
    private Paint SouthArrow = new Paint();

    public Compass(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        NorthArrow.setColor(Color.BLUE);
        NorthArrow.setStrokeWidth(10);
        SouthArrow.setColor(Color.RED);
        SouthArrow.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        float len = Math.min(width, height) / 2.3f;
        double abs = Math.sqrt(x * x + y * y);
        float xc = width / 2;
        float yc = height / 2;
        double x1 = len * x / abs;
        double y1 = len * y / abs;

        canvas.drawLine(xc, yc, (float)(xc + x1), (float)(yc + y1), NorthArrow);
        canvas.drawLine(xc, yc, (float)(xc - x1), (float)(yc - y1), SouthArrow);
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
}
