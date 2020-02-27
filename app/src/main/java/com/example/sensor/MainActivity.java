package com.example.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView accelerateX;
    private TextView accelerateY;
    private TextView accelerateZ;

    private TextView angulX;
    private TextView angulY;
    private TextView angulZ;

    private TextView orientationX;
    private TextView orientationY;
    private TextView orientationZ;

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accelerateX = (TextView) findViewById(R.id.accelerateX);
        accelerateY = (TextView) findViewById(R.id.accelerateY);
        accelerateZ = (TextView) findViewById(R.id.accelerateZ);

        angulX = (TextView) findViewById(R.id.angulX);
        angulY = (TextView) findViewById(R.id.angulY);
        angulZ = (TextView) findViewById(R.id.angulZ);

        orientationX = (TextView) findViewById(R.id.orientationX);
        orientationY = (TextView) findViewById(R.id.orientationY);
        orientationZ = (TextView) findViewById(R.id.orientationZ);


        //获取传感器管理对象
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //获取传感器类型，三个传感器分别为加速度传感器、方向传感器、角速度传感器
        Sensor sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor sensor3 = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        //注册传感器监听器
        sensorManager.registerListener(listener1, sensor1, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener2, sensor2, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener3, sensor3, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener1);
        }
    }

    private SensorEventListener listener1 = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];

            accelerateX.setText("加速度X: " + xValue);
            accelerateY.setText("加速度Y: " + yValue);
            accelerateZ.setText("加速度Z: " + zValue);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private SensorEventListener listener2 = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];

            orientationX.setText("角速度X: " + xValue);
            orientationY.setText("角速度Y: " + yValue);
            orientationZ.setText("角速度Z: " + zValue);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private SensorEventListener listener3 = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];

            angulX.setText("方向X: " + xValue);
            angulY.setText("方向Y: " + yValue);
            angulZ.setText("方向Z: " + zValue);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
