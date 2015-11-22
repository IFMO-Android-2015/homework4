package au_rikka.compass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    boolean isCalculated = false;
    int newWidth, newHeight;
    int wShift, hShift;
    double pointerLen;
    Paint p;
    Bitmap myBitmap;

    private Sensor magnSensor;
    private SensorManager mySensorManager;

    float x_val, y_val;

    DrawView myDrawView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDrawView = new DrawView(this);
        setContentView(myDrawView);

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (magnSensor == null) {
            Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(this, "Sensor is not found", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    protected class DrawView extends View {
        public DrawView(MainActivity mainActivity) {
            super(mainActivity);
            myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.compass);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (!isCalculated) {
                double w = getWidth() - 20;
                double h = getHeight() - 20;
                double t = myBitmap.getWidth() / w;
                if (t < myBitmap.getHeight() / h) {
                    t = myBitmap.getHeight() / h;
                }
                newHeight = (int) (myBitmap.getHeight() / t);
                newWidth = (int) (myBitmap.getWidth() / t);

                myBitmap = Bitmap.createScaledBitmap(myBitmap, newWidth, newHeight, true);

                //magic consts to make this shit looks good (picture's center is not in the center)
                wShift = (int) ((getWidth() - newWidth) / 2 - newWidth / 6.1 * 0.078);
                hShift = (int) ((getHeight() - newHeight) / 2 - newHeight / 6.8 * 0.268);
                pointerLen = newWidth / 6.5 * 2.48;

                p = new Paint();
                p.setColor(Color.RED);
                p.setStrokeWidth(10);

                isCalculated = true;
            }

            canvas.drawBitmap(myBitmap, wShift, hShift, null);

            double len = Math.sqrt(x_val * x_val + y_val * y_val);
            int x = getWidth() / 2 + (int) ((x_val / len) * pointerLen);
            int y = getHeight() / 2 - (int) ((y_val / len) * pointerLen);
            canvas.drawLine(getWidth() / 2, getHeight() / 2, x, y,  p);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener(this, magnSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x_val = event.values[0];
        y_val = event.values[1];
        myDrawView.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
