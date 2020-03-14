package com.example.sensor;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private TextView time;
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
    private MyDatabaseHelper dbHelper;
    public boolean isRecord;

    public String time_value;
    public float accelerateX_value;
    public float accelerateY_value;
    public float accelerateZ_value;
    public float angulX_value;
    public float angulY_value;
    public float angulZ_value;
    public float orientationX_value;
    public float orientationY_value;
    public float orientationZ_value;

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

        addData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener1);
            sensorManager.unregisterListener(listener2);
            sensorManager.unregisterListener(listener3);
        }
    }

    private SensorEventListener listener1 = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            getTime(); //时间相关，放在这里调用保证时间更新

            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];

            accelerateX.setText("加速度X: " + xValue);
            accelerateY.setText("加速度Y: " + yValue);
            accelerateZ.setText("加速度Z: " + zValue);

            accelerateX_value = xValue;
            accelerateY_value = yValue;
            accelerateZ_value = zValue;
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

            angulX.setText("角速度X: " + xValue);
            angulY.setText("角速度Y: " + yValue);
            angulZ.setText("角速度Z: " + zValue);

            angulX_value = xValue;
            angulY_value = yValue;
            angulZ_value = zValue;
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

            orientationX.setText("方向X: " + xValue);
            orientationY.setText("方向Y: " + yValue);
            orientationZ.setText("方向Z: " + zValue);

            orientationX_value = xValue;
            orientationY_value = yValue;
            orientationZ_value = zValue;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    //时间相关方法
    private void getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss.SSS");
        Date mDate =  new Date(System.currentTimeMillis());
        String myTime = formatter.format(mDate);
        time = (TextView)findViewById(R.id.myTime);
        time.setText("当前时间："+ myTime);
        time_value = formatter.format(mDate);
    }
    //添加数据方法
    private void addData(){
        dbHelper = new MyDatabaseHelper(this,"SQLite1.db",null,1);
        dbHelper.getWritableDatabase();
        Button start = (Button)findViewById(R.id.addData_start);
        Button end = (Button)findViewById(R.id.addData_end);
        Button clear = (Button)findViewById(R.id.clear);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"开始保存",Toast.LENGTH_SHORT).show();
                isRecord = true;
                if (isRecord = true) {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("time", time_value);
                            values.put("accelerateX", accelerateX_value);
                            values.put("accelerateY", accelerateY_value);
                            values.put("accelerateZ", accelerateZ_value);
                            values.put("angulX", angulX_value);
                            values.put("angulY", angulY_value);
                            values.put("angulZ", angulZ_value);
                            values.put("orientationX", orientationX_value);
                            values.put("orientationY", orientationY_value);
                            values.put("orientationZ", orientationZ_value);
                            db.insert("Sensor1", null, values);
                        }
                    }, 0, 200);
                }
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRecord = false;
                Toast.makeText(MainActivity.this,"结束保存",Toast.LENGTH_SHORT).show();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Sensor1","id > ?",new String[]{"0"});
                Toast.makeText(MainActivity.this,"清除完成",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
