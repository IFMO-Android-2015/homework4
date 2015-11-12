package compass_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by novik on 12.11.15.
 */
public class CompassView extends View implements SensorEventListener {
    private float sin, cos;

    private final Paint paintNorth = new Paint();
    private final Paint paintSouth = new Paint();
    private final Paint paintCircle = new Paint();

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintNorth.setColor(Color.BLUE);
        paintSouth.setColor(Color.RED);
        paintCircle.setColor(Color.YELLOW);

        paintNorth.setStrokeWidth(15);
        paintSouth.setStrokeWidth(15);
        paintCircle.setStrokeWidth(20);

        paintCircle.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float length = Math.min(width, height) / 2.2f;
        //length is a radius of circle and length of line. Dividing by 2.2 for better look.

        int centerX = width / 2;
        int centerY = height / 2;

        canvas.drawCircle(centerX, centerY, length, paintCircle);

        canvas.drawLine(centerX, centerY
        , centerX + length * cos
        , centerY + length * sin
        , paintNorth);

        canvas.drawLine(centerX, centerY
                , centerX + length * (-cos)
                , centerY + length * (-sin)
                , paintSouth);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float abs = (float) Math.sqrt(event.values[0] * event.values[0] + event.values[1] * event.values[1]);
        sin = event.values[0] / abs;
        cos = event.values[1] / abs;

        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
