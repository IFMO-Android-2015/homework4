package com.nickmudry.homework4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

/**
 * Created by hp on 15.11.2015.
 */
public class CompasView extends View implements SensorEventListener {

    double alpha, betta;
    int center_x, center_y, radius;
    Paint circle, line_1, text, line_2;
    SensorManager sensorManager;
    Sensor sensorMagnetic, sensorGravity;
    float[] gravity, magnetic;
    public CompasView(Context context ){
        super(context);
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        center_x = getResources().getDisplayMetrics().widthPixels / 2;
        center_y = getResources().getDisplayMetrics().heightPixels / 2;
        radius = (int)(center_x * 0.9);

        circle = new Paint();
        circle.setColor(Color.CYAN);
        circle.setStrokeWidth(10 * radius / 360);
        circle.setStyle(Paint.Style.STROKE);
        circle.setAlpha(70);

        line_1 = new Paint();
        line_1.setColor(Color.BLUE);
        line_1.setStrokeWidth(10 * radius / 360);
        line_1.setStyle(Paint.Style.STROKE);

        text = new Paint();
        text.setColor(Color.GREEN);
        text.setTextSize(25);

        line_2 = new Paint();
        line_2.setColor(Color.RED);
        line_2.setStrokeWidth(10 * radius / 360);
        line_2.setStyle(Paint.Style.STROKE);

        betta = 0;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values;
        } else {
            magnetic = event.values;
        }
        if (gravity != null && magnetic != null) {
            float[] R = new float[9], I = new float[9];
            boolean res = sensorManager.getRotationMatrix(R, I, gravity, magnetic);
            if (res) {
                float[] orientation = new float[3];
                sensorManager.getOrientation(R, orientation);
                betta = orientation[0] * 180 / Math.PI;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void setAlpha() {
        if (betta < 0) {
            betta += 360;
        }
        if (Math.abs(alpha - betta) > 4) {
            if (Math.abs(alpha - betta) > 180) {
                if (betta < alpha) {
                    alpha += 3;
                } else {
                    alpha -= 3;
                }
            } else if (betta > alpha) {
                alpha += 3;
            } else {
                alpha -= 3;
            }
        }
        if (alpha < 0) {
            alpha += 360;
        }
        if (alpha > 360) {
            alpha -= 360;
        }
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(center_x, center_y, radius, circle);
        canvas.drawText("N", center_x, center_y + radius, text);
        canvas.drawText("S", center_x, center_y - radius, text);
        canvas.drawText("W", center_x - radius, center_y, text);
        canvas.drawText("E", center_x + radius, center_y, text);
        setAlpha();
        int dy = (int)(Math.cos(alpha / 180 * Math.PI) * radius);
        int dx = (int)(Math.sin(alpha / 180 * Math.PI) * radius);
        canvas.drawLine(center_x, center_y, center_x + dx, center_y + dy, line_1);
        canvas.drawLine(center_x, center_y, center_x - dx, center_y - dy, line_2);
        invalidate();
    }
}
