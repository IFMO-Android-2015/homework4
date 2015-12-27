package com.example.annafrolova.compass;

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
 * Created by annafrolova on 27.12.15.
 */
public class CompassView extends View implements SensorEventListener {

    private final Paint arrowPaint = new Paint();
    private final Paint circlePaint = new Paint();

    private float rx = 1, ry = 0;

    public static final String TAG = "CompassView";

    public CompassView(Context context) {
        super(context);
        init();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        arrowPaint.setColor(Color.BLUE);
        arrowPaint.setStrokeWidth(10f);

        circlePaint.setColor(Color.WHITE);
        circlePaint.setStrokeWidth(20f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        float r = Math.min(width, height) / 2 - 5f;

        float l = (float) Math.sqrt(rx * rx + ry * ry);

        float x = rx * r / l;
        float y = ry * r / l;

        float cx = width / 2;
        float cy = height / 2;

        canvas.drawCircle(cx, cy, r, circlePaint);
        canvas.drawLine(cx, cy ,cx + x, cy + y, arrowPaint);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        rx = event.values[0];
        ry = -event.values[1];

        Log.d(TAG,"Sensor changed: rx = " + rx + ", ry = " + ry);

        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
