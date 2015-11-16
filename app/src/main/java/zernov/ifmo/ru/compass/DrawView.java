package zernov.ifmo.ru.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DrawView extends View implements SensorEventListener {
    float[] accMatrix, magnetMatrix, rMatrix, resultMatrix, iMatrix;
    public float result;
    Paint paint = new Paint();

    public DrawView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    protected void onDraw(Canvas canvas) {
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        float r = Math.min(x, y) * 0.95f;
        canvas.rotate(-(float) Math.toDegrees(result), x, y);
        paint.setColor(Color.BLUE);
        canvas.drawLine(x, y, x, y - r, paint);
        paint.setColor(Color.RED);
        canvas.drawLine(x, y + r, x, y, paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(x, y, r, paint);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accMatrix = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magnetMatrix = event.values;
        if (accMatrix != null && magnetMatrix != null) {
            rMatrix = new float[9];
            iMatrix = new float[9];
            if (SensorManager.getRotationMatrix(rMatrix, iMatrix, accMatrix, magnetMatrix)) {
                resultMatrix = new float[3];
                SensorManager.getOrientation(rMatrix, resultMatrix);
                result = resultMatrix[0];
                Log.d(TAG, Float.toString(- (float) Math.toDegrees(result)));
            }
        }
        invalidate();
    }

    private static final String TAG = "Draw";


}