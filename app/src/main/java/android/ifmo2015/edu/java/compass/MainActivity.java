package android.ifmo2015.edu.java.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetic_field;
    private ImageView compassView;
    private float curDegree = 0f;
    private float[] lastAccelerometer = new float[3];
    private float[] lastMagnetic_Field = new float[3];
    private float[] orientation = new float[3];
    private float[] tensor = new float[9];

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic_field = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        compassView = (ImageView) findViewById(R.id.compass);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnetic_field, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometer);
        sensorManager.unregisterListener(this, magnetic_field);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {
            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
        } else if (event.sensor == magnetic_field) {
            System.arraycopy(event.values, 0, lastMagnetic_Field, 0, event.values.length);
        }
        if (lastAccelerometer != null && lastMagnetic_Field != null) {
            SensorManager.getRotationMatrix(tensor, null, lastAccelerometer, lastMagnetic_Field);
            SensorManager.getOrientation(tensor, orientation);
            float angleRads = orientation[0];
            float angleDegrees = (int)(-((float)(Math.toDegrees(angleRads) + 360) % 360)); //cast to int for smoother angles
            RotateAnimation rotateAnimation = new RotateAnimation(
                    curDegree,
                    angleDegrees,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            rotateAnimation.setDuration(300);
            rotateAnimation.setFillAfter(true);

            compassView.startAnimation(rotateAnimation);
            curDegree = angleDegrees;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
