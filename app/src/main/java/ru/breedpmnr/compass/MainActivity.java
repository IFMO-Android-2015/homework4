package ru.breedpmnr.compass;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Compass comp;
    private Sensor sens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sens = ((SensorManager)getSystemService(SENSOR_SERVICE)).getDefaultSensor(sens.TYPE_MAGNETIC_FIELD);
        comp = (Compass) findViewById(R.id.view);
        ((SensorManager)getSystemService(SENSOR_SERVICE)).registerListener(comp,sens,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((SensorManager)getSystemService(SENSOR_SERVICE)).unregisterListener(comp);
    }
}
