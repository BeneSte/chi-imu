package com.example.myapplication.entities;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = GyroEntity.TABLE_NAME)
public class GyroEntity {

    public static final String TABLE_NAME = "gyroscope";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_GYRO_X = "gyroX";
    public static final String COLUMN_GYRO_Y = "gyroY";
    public static final String COLUMN_GYRO_Z = "gyroZ";
    public static final String COLUMN_SYNCED = "synced";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_TIME)
    private long time;

    @ColumnInfo(name = COLUMN_GYRO_X)
    private float gyroX;

    @ColumnInfo(name = COLUMN_GYRO_Y)
    private float gyroY;

    @ColumnInfo(name = COLUMN_GYRO_Z)
    private float gyroZ;


    public GyroEntity(float gyroX, float gyroY, float gyroZ){
        this.time = System.currentTimeMillis();
        this.gyroX = gyroX;
        this.gyroY = gyroY;
        this.gyroZ = gyroZ;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getGyroX() {
        return gyroX;
    }

    public void setGyroX(float gyroX) {
        this.gyroX = gyroX;
    }

    public float getGyroY() {
        return gyroY;
    }

    public void setGyroY(float gyroY) {
        this.gyroY = gyroY;
    }

    public float getGyroZ() {
        return gyroZ;
    }

    public void setGyroZ(float gyroZ) {
        this.gyroZ = gyroZ;
    }

}
