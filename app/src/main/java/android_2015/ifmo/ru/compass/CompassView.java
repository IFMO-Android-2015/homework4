package android_2015.ifmo.ru.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


public class CompassView extends View implements SensorEventListener {

    Paint paint = new Paint();
    float x = 0, y = 0;

    public CompassView(Context context) {
        super(context);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    DisplayMetrics metrics = new DisplayMetrics();

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        int center_x = metrics.widthPixels / 2;
        int center_y = metrics.heightPixels / 2;

        int radius = metrics.heightPixels / 6;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        //circle
        canvas.drawCircle(center_x, center_y, radius, paint);
        paint.setColor(Color.BLUE);
        //line
        float norm = 1 / (float) Math.sqrt(x * x + y * y);
        int stopX = (int) (center_x + radius * x * norm);
        int stopY = (int) (center_y + radius * y * norm);
        canvas.drawLine(center_x, center_y, stopX, stopY, paint);
        invalidate();
    }

}
