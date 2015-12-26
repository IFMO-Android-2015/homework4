package com.vantuz.compass.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {
    private float x;
    private float y;
    private final Paint paintBlue = new Paint();
    private final Paint paintRed = new Paint();

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintBlue.setColor(Color.BLUE);
        paintRed.setColor(Color.RED);

        paintBlue.setStrokeWidth(15);
        paintRed.setStrokeWidth(15);
    }

    public void setValues(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float hypothenose = (float) Math.sqrt(x * x + y * y);
        float sin = y / hypothenose;
        float cos = x / hypothenose;
        int width = getWidth();
        int height = getHeight();
        float centerX = width / 2f;
        float centerY = height / 2f;
        float length = Math.min(centerX, centerY);
        canvas.drawLine(centerX, centerY, centerX + length * cos, centerY + length * sin, paintBlue);
        canvas.drawLine(centerX, centerY, centerX + (length * cos * -1), centerY + (length * sin * -1), paintRed);
    }

    public void redraw() {
        invalidate();
    }
}
