package com.example.homework4.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;

import com.example.homework4.R;

public class CompassView extends View implements SensorEventListener {
    private float x = 0, y = 0;

    private final Paint nothArrow = new Paint();
    private final Paint southArrow = new Paint();
    private Bitmap imageCompass;

    public CompassView(Context contex, AttributeSet attrs) {
        super(contex, attrs);
        nothArrow.setColor(Color.BLUE);
        southArrow.setColor(Color.RED);

        nothArrow.setStrokeWidth(10);
        southArrow.setStrokeWidth(10);

        imageCompass = BitmapFactory.decodeResource(getResources(), R.drawable.compass_back);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        float abs = (float) Math.sqrt(x * x + y * y);
        float cos = x / abs;
        float sin = y / abs;
        float R = Math.min(width, height) / 2.2f;

        int newSide = Math.min(width, height) - 10;
        imageCompass = imageCompass.createScaledBitmap(imageCompass, newSide, newSide, true);

        canvas.drawBitmap(imageCompass,
                (float) centerX - (float) imageCompass.getWidth() / 2,
                (float) centerY - (float) imageCompass.getHeight() / 2,
                null);

        canvas.drawLine(centerX, centerY, centerX + R * cos, centerY + R * sin, nothArrow);
        canvas.drawLine(centerX, centerY, centerX + R * (-cos), centerY + R * (-sin), southArrow);
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