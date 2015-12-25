package com.example.home.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;

public class Compass extends View implements SensorEventListener {
    private float X, Y;
    private final Paint ArrowN = new Paint();
    private final Paint ArrowS = new Paint();

    public Compass(Context context, AttributeSet attributes) {
        super(context, attributes);
        ArrowN.setColor(Color.BLUE);
        ArrowN.setStrokeWidth(10);
        ArrowS.setColor(Color.RED);
        ArrowS.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();
        float center = Math.min((int) width, (int) height) / 3f;
        width /= 2;
        height /= 2;
        double R = Math.sqrt(X * X + Y * Y);
        double dx = center * X / R;
        double dy = center * Y / R;
        canvas.drawLine(width, height, (float) (width + dx), (float) (height - dy), ArrowN);
        canvas.drawLine(width, height, (float) (width - dx), (float) (height + dy), ArrowS);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        X = event.values[0];
        Y = event.values[1];
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int changed) {
    }
}
