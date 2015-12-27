package ru.eadm.homework4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {

    private float angle;
    private final Paint paintNorth = new Paint();
    private final Paint paintSouth = new Paint();

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintNorth.setColor(0xFF15C7CA); paintNorth.setStrokeWidth(10);
        paintSouth.setColor(0xFFEE3853); paintSouth.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    public void changeAngle(final float angle) {
        this.angle = angle - 90;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(getWidth()/2, getHeight()/2,
                (float) (getWidth()/2 + getWidth()/4 * Math.cos(Math.toRadians(angle))), (float) (getHeight()/2 + getWidth()/4 * Math.sin(Math.toRadians(angle))), paintNorth);
        canvas.drawLine(getWidth()/2, getHeight()/2,
                (float) (getWidth()/2 + getWidth()/4 * Math.cos(Math.toRadians(angle - 180))), (float) (getHeight()/2 + getWidth()/4 * Math.sin(Math.toRadians(angle - 180))), paintSouth);
//        angle += 0.1;
        invalidate();
    }
}
