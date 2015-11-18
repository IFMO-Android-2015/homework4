package com.example.vorona.bombombom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by vorona on 17.11.15.
 */
public class AngleView extends View {
    private float angle;

    private final Paint paintAngle = new Paint();

    public AngleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintAngle.setColor(Color.RED);
        paintAngle.setStrokeWidth(10);
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int minSize = Math.min(width, height) / 2;

        int centerX = width / 2;
        int centerY = height / 2;
        canvas.drawLine(centerX, centerY, (float)(centerX + minSize * Math.cos(angle) ),
                (float)(centerY + minSize * Math.sin(angle)) , paintAngle);

//        angle += 0.1;
        invalidate();
    }
}
