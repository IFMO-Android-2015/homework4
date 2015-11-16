package ru.npoperechnyi.homework4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class CompassBackground extends View {
    private Paint circlePaint;

    public CompassBackground(Context context) {
        super(context);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public CompassBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public CompassBackground(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int h = getMeasuredHeight();
        int w = getMeasuredWidth();
        int r = w < h ? w / 2 : h / 2;

        circlePaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(w / 2, h / 2, r, circlePaint);
        for (int i = 0; i < 360; i += 10) {
            float Xcoord = w / 2 + (float) Math.cos(Math.toRadians(i)) * r;
            float Ycoord = h / 2 - (float) Math.sin(Math.toRadians(i)) * r;
            if (i % 90 != 0) {
                canvas.drawLine(Xcoord,
                        Ycoord,
                        (w * 0.03125f + Xcoord * 0.9375f),
                        (h * 0.03125f + Ycoord * 0.9375f),
                        circlePaint
                );
            } else {
                canvas.drawLine(Xcoord,
                        Ycoord,
                        (w * 0.125f + Xcoord * 0.75f),
                        (h * 0.125f + Ycoord * 0.75f),
                        circlePaint
                );
            }
        }
    }
}
