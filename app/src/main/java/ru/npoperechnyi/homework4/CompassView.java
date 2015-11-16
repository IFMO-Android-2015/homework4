package ru.npoperechnyi.homework4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {
    private Paint pointerPaint;
    private float direction = 0;

    public CompassView(Context context) {
        super(context);
        pointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }
    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        pointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        pointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int h = getMeasuredHeight();
        int w = getMeasuredWidth();
        int r = w < h ? w / 2 : h / 2;


        pointerPaint.setStyle(Paint.Style.STROKE);
        pointerPaint.setColor(Color.RED);
        canvas.drawLine(w / 2, h / 2,       //Start
                w / 2 + r * (float) Math.sin(direction)*0.81f,          //X end
                h / 2 - r * (float) Math.cos(direction)*0.81f,          //Y end
                pointerPaint);

        pointerPaint.setColor(Color.BLUE);
        canvas.drawLine(w / 2, h / 2,       //Start
                w / 2 - r * (float) Math.sin(direction)*0.81f,          //X end
                h / 2 + r * (float) Math.cos(direction)*0.81f,          //Y end
                pointerPaint);
    }

    public void update(float newDirection) {
        if (Math.abs(direction + newDirection) > 0.0005) {
            direction = -newDirection;
            invalidate();
        }
    }
}
