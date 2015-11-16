package lec.homework4;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CompassActivity extends AppCompatActivity {

    SensorManager manager;
    Sensor magSensor;

    CompassView compassView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        compassView = (CompassView) findViewById(R.id.view_compass);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(compassView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(compassView, magSensor, SensorManager.SENSOR_DELAY_UI);
    }
}
