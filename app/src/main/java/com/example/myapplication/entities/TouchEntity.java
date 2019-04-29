package com.example.myapplication.entities;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = TouchEntity.TABLE_NAME)
public class TouchEntity {

    public static final String TABLE_NAME = "touches";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_FINGER_ID = "fingerID";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_X = "x";
    public static final String COLUMN_Y = "y";
    public static final String COLUMN_PRESSURE = "pressure";
    public static final String COLUMN_SYNCED = "synced";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_TIME)
    private long time;

    @ColumnInfo(name = COLUMN_FINGER_ID)
    private int fingerID;

    @ColumnInfo(name = COLUMN_TYPE)
    private String type;

    @ColumnInfo(name = COLUMN_X)
    private int xCoord;

    @ColumnInfo(name = COLUMN_Y)
    private int yCoord;

    @ColumnInfo(name = COLUMN_PRESSURE)
    private double pressure;

    @ColumnInfo(name = COLUMN_SYNCED)
    private boolean synced;

    public TouchEntity(int fingerID, String type, int x, int y, double pressure){
        this.time = System.currentTimeMillis();
        this.fingerID = fingerID;
        this.type = type;
        this.xCoord = x;
        this.yCoord = y;
        this.pressure = pressure;
        this.synced = false;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getFingerID() {
        return fingerID;
    }

    public void setFingerID(int fingerID) {
        this.fingerID = fingerID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure){
        this.pressure = pressure;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }
}
