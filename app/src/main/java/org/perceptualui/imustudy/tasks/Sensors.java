package org.perceptualui.imustudy.tasks;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.perceptualui.imustudy.AppDatabase;
import org.perceptualui.imustudy.entities.AccEntity;
import org.perceptualui.imustudy.entities.GyroEntity;
import org.perceptualui.imustudy.entities.OriEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Sensors {

    private final String TAG = this.getClass().getSimpleName();
    private final Sensor gyrometer;
    private final Sensor accelerometer;
    private final Sensor accelerometer_lin;
    private final Sensor magnetometer;

    private Executor executor;

    private AppDatabase db;

    SensorManager sensorManager;
    private boolean[] sensorReady = new boolean[6];

    public SensorEventListener AccLinListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) {
        }

        public void onSensorChanged(SensorEvent event) {
            if (!sensorReady[0]) {
                sensorReady[0] = true;
            }
            AccEntity accEntity = new AccEntity(event.values[0], event.values[1], event.values[2]);
            executor.execute(() -> db.accDao().insertAcc(accEntity));
            // Log.d(TAG, event.sensor.getName() + System.currentTimeMillis() + ": \t\tX: " + event.values[0] + ";\t\tY: " + event.values[1] + ";\t\tZ: " + event.values[2]);
        }
    };
    public SensorEventListener GyroListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) {
        }

        public void onSensorChanged(SensorEvent event) {
            if (!sensorReady[1]) {
                sensorReady[1] = true;
            }
            GyroEntity gyroEntity = new GyroEntity(event.values[0], event.values[1], event.values[2]);
            executor.execute(() -> db.gyroDao().insertGyro(gyroEntity));
            //Log.d(TAG, event.sensor.getName() + System.currentTimeMillis() + ": \t\tX: " + event.values[0] + ";\t\tY: " + event.values[1] + ";\t\tZ: " + event.values[2]);
        }
    };

    public SensorEventListener OriListener = new SensorEventListener() {
        float[] mGravity;
        float[] mGeomagnetic;

        public void onAccuracyChanged(Sensor sensor, int acc) {
        }

        public void onSensorChanged(SensorEvent event) {
            if (!sensorReady[5]) {
                sensorReady[5] = true;
            }
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                mGravity = event.values;
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                mGeomagnetic = event.values;
            if (mGravity != null && mGeomagnetic != null) {
                float R[] = new float[9];
                float I[] = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
                if (success) {
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    // Log.d(TAG, "Orientation " + System.currentTimeMillis() + ": \t\tX: " + orientation[1] + ";\t\tY: " + orientation[2] + ";\t\tZ: " + orientation[0]);
                    OriEntity oriEntity = new OriEntity(orientation[0], orientation[1], orientation[2], 0.0f);
                    executor.execute(() -> db.oriDao().insertOri(oriEntity));
                    //Log.d(TAG, "ori " + orientation[0]+  orientation[1] + orientation[2]);
                }
            }
        }
    };

    public Sensors(Context context) {

        this.db = AppDatabase.getInstance(context);
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        executor = Executors.newSingleThreadExecutor();
        gyrometer = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer_lin = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        onResume();
    }

    public void onResume() {
        sensorManager.registerListener(AccLinListener, accelerometer_lin, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(GyroListener, gyrometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(OriListener, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(OriListener, magnetometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void onStop() {
        sensorManager.unregisterListener(AccLinListener);
        sensorManager.unregisterListener(GyroListener);
        sensorManager.unregisterListener(OriListener);
    }

    public boolean allSensorsactive() {
        for (boolean b : sensorReady) if (!b) return false;
        return true;
    }
}
