package com.example.domashka351;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private float[] lights;
    MediaPlayer mp = new MediaPlayer();
    boolean sens = false;
    TextView kek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        lights = sensorEvent.values;
        kek = (TextView)findViewById(R.id.editTextNumber);
        kek.setText(Integer.toString((int)lights[0]));
        if(lights[0] < 50){
            mp = MediaPlayer.create(this, R.raw.muzica);
            sens = true;
            mp.start();
        }
        else if(lights[0] >= 50){
            mp.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        mp.stop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        if(i > 50 && sens) {
            mp.stop();
        }
    }
}