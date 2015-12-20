package yakutovd.com.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    private static final Paint redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /*
     *  North directed arrow
     */
    private static final Paint bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

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
        redPaint.setColor(Color.parseColor("#A80000"));
        redPaint.setStrokeWidth(10);
        redPaint.setStyle(Paint.Style.FILL);
        redPaint.setAntiAlias(true);
        bluePaint.setColor(Color.parseColor("#003870"));
        bluePaint .setStrokeWidth(10);
        bluePaint.setStyle(Paint.Style.FILL);
        bluePaint.setAntiAlias(true);
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
        float base = (float) (0.13 * radius);
        drawTriangle(centerX + radius, centerY, centerX, centerY + base / 2, centerX, centerY - base / 2, redPaint, canvas);
        drawTriangle(centerX - radius, centerY, centerX, centerY + base / 2, centerX, centerY - base / 2, bluePaint, canvas);
    }

    private void drawTriangle(float ax, float ay, float bx, float by, float cx, float cy, Paint paint, Canvas canvas) {
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(ax, ay);
        path.lineTo(bx, by);
        path.lineTo(cx, cy);
        path.lineTo(ax, ay);
        path.close();
        canvas.drawPath(path, paint);
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
