package com.nickmudry.homework4;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class CompasActivity extends Activity {

    CompasView compass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        compass = new CompasView(this);
        setContentView(compass);

    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.sensorManager.registerListener(compass, compass.sensorMagnetic, compass.sensorManager.SENSOR_DELAY_FASTEST);
        compass.sensorManager.registerListener(compass, compass.sensorGravity, compass.sensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    protected void onPause() {
        super.onPause();
        compass.sensorManager.unregisterListener(compass);
    }

}
