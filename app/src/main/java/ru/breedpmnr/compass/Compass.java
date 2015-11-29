package ru.breedpmnr.compass;

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
 * Created by Lenovo on 29.11.2015.
 */
public class Compass extends View implements SensorEventListener {
    private float vect[];
    private Paint N;
    private Paint C;
    public Compass(Context context,AttributeSet attrs) {
        super(context,attrs);
        vect = new float[2];
        N = new Paint();
        C = new Paint();
        N.setColor(Color.BLUE);
        C.setColor(Color.GREEN);
        N.setStrokeWidth(20);
        C.setStrokeWidth(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        canvas.drawCircle(width/2,height/2,width/2,C);
        canvas.drawLine(width/2,height/2,width/2 + width/2*vect[0],height/2 + width/2*vect[1],N);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double norm = Math.sqrt(Math.pow(event.values[0],2) + Math.pow(event.values[1],2));
        vect[0] = event.values[0]/(float)norm;
        vect[1] = event.values[1]/(float)norm;
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
