package android_2015.ifmo.ru.compass;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SensorManager manager;
    private Sensor magnetic;
    private CompassView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new CompassView(getApplicationContext());
        setContentView(view);
        initSensor();
    }

    private void registerSensor() {
        manager.registerListener(view, magnetic, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void unregisterSensor() {
        manager.unregisterListener(view);
    }

    private void initSensor() {
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetic = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        registerSensor();
    }

    protected void onResume() {
        super.onResume();
        registerSensor();
    }

    protected void onPause() {
        super.onPause();
        unregisterSensor();
    }
}
