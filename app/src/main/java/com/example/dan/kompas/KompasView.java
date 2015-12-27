package com.example.dan.kompas;

/**
 * Created by Dan) on 20.12.2015.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class KompasView extends View {
    Paint bluePaint = new Paint();
    Paint redPaint = new Paint();

    private double angle;
    public KompasView(Context context, AttributeSet attrs) {
            super(context, attrs);
            angle = 0;
            bluePaint.setColor(Color.BLUE);
            bluePaint.setStrokeWidth(6);
            redPaint.setColor(Color.RED);
            redPaint.setStrokeWidth(6);
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    private double radius;
    private double centerX, centerY;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine((float) centerX, (float) centerY, (float) (centerX + Math.cos(angle) * radius), (float) (centerY - Math.sin(angle) * radius),
                        redPaint);
        canvas.drawLine((float) centerX, (float) centerY, (float) (centerX - Math.cos(angle) * radius), (float) (centerY + Math.sin(angle) * radius),
                        bluePaint);
        invalidate();
    }


    protected void onSizeChanged(int w, int h, int w1, int h1) {
        super.onSizeChanged(w, h, w1, h1);
        centerX = ((double) w) / 2;
        centerY = ((double) h) / 2;
        radius = Math.min(centerX, centerY) * 4 / 5;
    }
}