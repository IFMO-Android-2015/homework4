package alexcom.homework4;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


public final class CompassActivity extends Activity implements SensorEventListener {

    private CompassView compassArrow;
    private Sensor magnetSensor;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compassArrow = (CompassView) findViewById(R.id.arrow);
        compassArrow.setAngle(0f);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        magnetSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        compassArrow.setAngle((float) Math.PI / 2f - event.values[0] * (float) Math.PI / 180f);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
