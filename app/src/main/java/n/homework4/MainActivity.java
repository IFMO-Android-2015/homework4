package n.homework4;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    float compassOrientation = 0.0f;
    float[] gravityMatrix;
    float[] geomagneticMatrix;
    ImageView compassArrow;

    private SensorManager sensorManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        compassArrow = (ImageView) findViewById(R.id.compassArrow);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
           gravityMatrix = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geomagneticMatrix = event.values;
        if (gravityMatrix != null && geomagneticMatrix != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, gravityMatrix, geomagneticMatrix);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                RotateAnimation animation = new RotateAnimation(toDeg(compassOrientation), toDeg(-orientation[0]), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                animation.setDuration(50);
                compassArrow.startAnimation(animation);
                compassOrientation = -orientation[0];
                compassArrow.invalidate();
            }
        }
    }
    float toDeg(float rad) {
        return (180.0f * rad) / (3.14159f);
    }
}
