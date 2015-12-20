package com.gaudima.gaudima.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class CompassView extends View implements SensorEventListener {
    private float angle;
    private Paint circle_paint = new Paint();
    private Paint line_paint = new Paint();

    TextView debugTextView;

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        circle_paint.setColor(Color.rgb(0, 0, 0));
        circle_paint.setStyle(Paint.Style.STROKE);
        circle_paint.setStrokeWidth(10);
        line_paint.setColor(Color.rgb(0, 0, 255));
        line_paint.setStrokeWidth(10);
    }

    void setDebugTextView(TextView debugTextView) {
        this.debugTextView = debugTextView;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        float radius = Math.min(height, width) / 2.1f;
        canvas.drawLine(centerX, centerY, (float)(centerX - radius * Math.sin(angle)), (float)(centerY - radius * Math.cos(angle)), line_paint);
        line_paint.setColor(Color.rgb(255, 0, 0));
        canvas.drawLine(centerX, centerY, (float) (centerX + radius * Math.sin(angle)), (float) (centerY + radius * Math.cos(angle)), line_paint);
        line_paint.setColor(Color.rgb(0, 0, 255));
        canvas.drawCircle(centerX, centerY, radius, circle_paint);
    }

    @Override
    public void onSensorChanged(SensorEvent sensor) {
        float[] Rot = new float[9];
        float[] orientation = new float[3];
        SensorManager.getRotationMatrixFromVector(Rot, sensor.values);
        SensorManager.getOrientation(Rot, orientation);

        debugTextView.setText(String.format(getResources().getString(R.string.debugInfo), orientation[0], orientation[1], orientation[2]));
        angle = orientation[0];
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
