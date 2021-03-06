package com.example.shabab.accelerometertest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorEventListener;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate=0;
    private float last_x, last_y, last_z;

    //sensitivity of the shaker
    private static final int SHAKE_THRESHOLD = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        senSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Sensor mySensor = event.sensor;

            if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER)
            {
                float x = event.values[0];
                float y = event.values[0];
                float z = event.values[0];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100)
            {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/diffTime*10000;
                if (speed > SHAKE_THRESHOLD)
                {

                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    protected void onPause()
    {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume()
    {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
