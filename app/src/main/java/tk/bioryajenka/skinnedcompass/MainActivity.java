package tk.bioryajenka.skinnedcompass;

/**
 * Created by Jackson on 28.11.2015.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {
    private static String TAG = "MyTag";

    private ImageView compassImage;
    private TextView infoTextView;

    private SensorManager sensorManager;

    private float currentDegree = 0f;
    private String pictureName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        infoTextView = (TextView) findViewById(R.id.infoTextView);
        compassImage = (ImageView) findViewById(R.id.compassImageView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (savedInstanceState == null) {
            pictureName = getPictureNames().get(0);
            loadPicture(pictureName);
        }
    }

    public void nextPicture(View view) {
        List<String> names = getPictureNames();
        int pos = names.indexOf(pictureName);
        if (pos == -1) {
            Log.e(TAG, "MainActivity.java:nextPicture: error");
            return;
        }
        pos++;
        if (pos == names.size()) {
            pos = 0;
        }
        loadPicture(names.get(pos));
    }

    public void prevPicture(View view) {
        List<String> names = getPictureNames();
        int pos = names.indexOf(pictureName);
        if (pos == -1) {
            Log.e(TAG, "MainActivity.java:nextPicture: error");
            return;
        }
        pos--;
        if (pos == -1) {
            pos = names.size() - 1;
        }
        loadPicture(names.get(pos));
    }

    private void loadPicture(String s) {
        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = getAssets().open(s);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            Log.e(TAG, "MainActivity.java:loadPicture: " + e);
            return;
        }
        compassImage.setImageBitmap(bitmap);
        RelativeLayout view = (RelativeLayout) findViewById(R.id.layout);
        if (s.endsWith("_black.png")) {
            view.setBackgroundColor(Color.BLACK);
        } else {
            view.setBackgroundColor(Color.WHITE);
        }
        view.invalidate();
        pictureName = s;
    }

    private List<String> getPictureNames() {
        List<String> result = new ArrayList<>();
        try {
            for (String s : getAssets().list("")) {
                if (s.endsWith(".png")) {
                    result.add(s);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "MainActivity.java:getPictureName: " + e);
        }
        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("pictureName", pictureName);
        savedInstanceState.putFloat("degree", currentDegree);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        loadPicture(savedInstanceState.getString("pictureName"));
        currentDegree = savedInstanceState.getFloat("degree");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (pictureName == null) return;

        float degree = Math.round(event.values[0]);

        infoTextView.setText(Float.toString(degree) + " degrees, " + pictureName.split("\\.")[0]);

        RotateAnimation animation = new RotateAnimation(currentDegree, -degree, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setFillAfter(true);
        animation.setDuration(150);

        compassImage.startAnimation(animation);

        currentDegree = -degree;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor
                .TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener in order to save battery
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}