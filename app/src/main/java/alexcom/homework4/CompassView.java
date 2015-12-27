package alexcom.homework4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class CompassView extends View {
    private float angle;

    private final Paint redArrow = new Paint();
    private final Paint blueArrow = new Paint();

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        redArrow.setColor(Color.RED);
        redArrow.setStrokeWidth(10);
        blueArrow.setColor(Color.BLUE);
        blueArrow.setStrokeWidth(10);
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int minSize = (int)(Math.min(width, height) / 2 * 0.8f);

        int centerX = width / 2;
        int centerY = height / 2;
        canvas.drawLine(centerX, centerY, (float) (centerX + minSize * Math.cos(angle)),
                (float) (centerY + minSize * Math.sin(angle)), redArrow);
        canvas.drawLine(centerX, centerY, (float)(centerX - minSize * Math.cos(angle)),
                (float)(centerY - minSize * Math.sin(angle)), blueArrow);

        invalidate();
    }
}