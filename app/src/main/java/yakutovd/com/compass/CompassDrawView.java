package yakutovd.com.compass;

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

public class CompassDrawView extends View implements SensorEventListener {

    /*
     *  South directed arrow
     */
    private static final Paint redPaint = new Paint();

    /*
     *  North directed arrow
     */
    private static final Paint bluePaint = new Paint();

    /*
     *  Circle around
     */
    private static final Paint blackPaint = new Paint();

    /*
     *  Background
     */
    private static final Paint backgroundPaint = new Paint();

    /*
     *  Background color
     */

    static {
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(10);
        bluePaint.setColor(Color.BLUE);
        bluePaint .setStrokeWidth(10);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(10);
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStrokeWidth(10);
    }

    float[] R;
    float[] I;
    float[] gravity;
    float[] geomagnetic;
    float[] current;

    private float currentAngle;

    public CompassDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentAngle = 0;
        R = new float[9];
        I = new float[9];
        current = new float[3];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        float radius = Math.min(centerX, centerY) * 0.9f;
        canvas.drawCircle(centerX, centerY, (float) (radius * 1.02), blackPaint);
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint);
        canvas.rotate(-currentAngle, centerX, centerY);
        canvas.drawLine(centerX, centerY, centerX + radius, centerY, redPaint);
        canvas.drawLine(centerX, centerY, centerX - radius, centerY, bluePaint);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values.clone();
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values.clone();
        }
        if (gravity != null && geomagnetic != null &&
                SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
            SensorManager.getOrientation(R, current);
            currentAngle = (float) Math.toDegrees(current[0]);
        }
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
