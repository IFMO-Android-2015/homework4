package rmnsv.compass;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sense;
    private ImageView img;
    private float the_degree = 0f;
    TextView degrees_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        degrees_show = (TextView) findViewById(R.id.text);
        sense = (SensorManager) getSystemService(SENSOR_SERVICE);
        img = (ImageView) findViewById(R.id.compass);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sense.registerListener(this, sense.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sense.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree_change = Math.round(event.values[0] * 10) / 10;
        degrees_show.setText("North: " + Float.toString(degree_change) + " deg.");

        RotateAnimation ra = new RotateAnimation(
                the_degree,
                -degree_change,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        ra.setDuration(400);
        ra.setFillAfter(true);
        img.startAnimation(ra);
        the_degree = -degree_change;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}