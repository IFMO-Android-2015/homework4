package com.example.alice.hw4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {

    float angle;
    Paint paintCircle = new Paint();
    Paint paintLine = new Paint();
    Paint paintSLine = new Paint();

    public CompassView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        paintCircle.setColor(Color.BLACK);
        paintCircle.setStrokeWidth(5);
        paintCircle.setStyle(Paint.Style.STROKE);

        paintLine.setColor(Color.GRAY);
        paintLine.setStrokeWidth(5);

        paintSLine.setColor(Color.RED);
        paintSLine.setStrokeWidth(5);

    }

    void setAngle(float angle) {
        this.angle = angle;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerCompassX = getWidth() / 2;
        float centerCompassY = getHeight() / 2;
        float radius = (float) ((Math.min(getWidth(), getHeight()) / 2) * 0.95);

        canvas.drawCircle(centerCompassX, centerCompassY, radius, paintCircle);
        canvas.drawLine(centerCompassX, centerCompassY - radius, centerCompassX, centerCompassY - radius - (float) (radius * 0.1), paintSLine);
        canvas.rotate(-angle, centerCompassX, centerCompassY);
        canvas.drawLine(centerCompassX, centerCompassY, centerCompassX, centerCompassY - radius, paintLine);
    }


}