package com.example.dominica.compas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by alexey.nikitin on 02.11.15.
 */
public class AngleView extends View {

    private final Paint nouth = new Paint();
    private final Paint south = new Paint();
    private float sin = 0;
    private float cos = 0;
    private float len = 0;

    public AngleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        nouth.setColor(Color.BLUE);
        nouth.setStrokeWidth(15);
        south.setStrokeWidth(15);
        south.setColor(Color.RED);

    }

    public void setAngle(float x) {
        this.sin = (float) Math.sin(x);
        this.cos = (float) Math.cos(x);
        this.len = (float) Math.sqrt(sin * sin + cos * cos);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float length = Math.min(width,height) / 2.5f;
        int centerX = width / 2;
        int centerY = height / 2;;
        canvas.drawLine(centerX, centerY, centerX - length * sin / len, centerY - length * cos / len, nouth);
        canvas.drawLine(centerX, centerY, centerX + length * sin / len, centerY + length * cos / len, south);
        invalidate();
    }
}
