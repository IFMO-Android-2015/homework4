package com.aleksej.compass.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.util.Map;
import java.util.Random;
import java.util.jar.Attributes;

/**
 * Created by Алексей on 10.11.2015.
 */
public class MyView extends View  implements SensorEventListener {
    private final Paint pen=new Paint();
    public double sin=0,cos=1;

    public MyView(Context context,AttributeSet attributes)
    {
        super(context, attributes);
        pen.setColor(new Random().nextInt());
        pen.setStrokeWidth(10);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int height=canvas.getHeight();
        int width=canvas.getWidth();
        int centerX=width/2;
        int centerY=height/2;
        int L=Math.min(centerX, centerY);

        canvas.drawLine(centerX, centerY,
                (float) (centerX + L * cos),
                (float) (centerY + L * sin),
                pen);
        //invalidate();
        //Log.w("view", "ondraw");

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double L = Math.sqrt(event.values[0]*event.values[0]+event.values[1]*event.values[1]);
        sin=event.values[0]/L;
        cos=event.values[1]/L;
        invalidate();
        Log.w("sensor", "chenged");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
