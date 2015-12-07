package homework03.compas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView view = new MyView(this);
        setContentView(view);
        sm = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);;
        sensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (savedInstanceState == null) {
            sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    float x;

    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        x = (float) (Math.PI * x / 180);
        Log.w("Here: ", x + "");
        sm.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putFloat("x", x);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        x = savedInstanceState.getFloat("x");
    }

    class MyView extends View {
        private Paint paintAngle = new Paint();
        private Paint paintRect = new Paint();
        private Paint paintCircle = new Paint();
        private Paint paintText = new Paint();

        public MyView(Context context) {
            super(context);
            paintAngle.setColor(Color.BLUE);
            paintAngle.setStrokeWidth(10);

            paintRect.setColor(Color.BLACK);
            paintRect.setStrokeWidth(10);

            paintCircle.setColor(Color.WHITE);
            paintCircle.setStrokeWidth(10);

            paintText.setColor(Color.WHITE);
            paintText.setTextSize(40);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float centerX = canvas.getWidth() / 2;
            float centerY = canvas.getHeight() / 2;
            float length = canvas.getHeight() / 4;

            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paintRect);
            canvas.drawCircle(centerX, centerY, length, paintCircle);

            float XX = (float) (Math.sin(x)) - 1;
            float YY = (float) (Math.cos(x)) - 1;
            float L = (float) Math.sqrt(XX * XX + YY * YY);
            canvas.drawLine(centerX, centerY, centerX + length * XX / L, centerY + length * YY / L, paintAngle);
            canvas.drawText("Blue line points on the North", centerX / 3, centerY / 3, paintText);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
