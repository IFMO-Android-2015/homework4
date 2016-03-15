package com.android.ifmo_android_2015.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {
    private Paint redArrow = new Paint();
    private Paint blueArrow = new Paint();
    private float x, y;


    public CompassView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        blueArrow.setColor(Color.BLUE);
        redArrow.setColor(Color.RED);
        blueArrow.setStrokeWidth(20);
        redArrow.setStrokeWidth(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float h = (float) Math.sqrt(x * x + y * y);
        float sin = y / h;
        float cos = x / h;
        float centerX = getWidth() / 2.0f;
        float centerY = getHeight() / 2.0f;
        float lineSize = Math.min(centerX, centerY);
        canvas.drawLine(centerX, centerY, centerX + lineSize * cos, centerY + lineSize * sin, blueArrow);
        canvas.drawLine(centerX, centerY, centerX  - lineSize * cos, centerY - lineSize * sin, redArrow);
    }

    public void rePaint(float x, float y) {
        this.x = x;
        this.y = y;
        invalidate();
    }
}
