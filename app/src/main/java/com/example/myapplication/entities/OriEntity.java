package com.example.myapplication.entities;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = OriEntity.TABLE_NAME)
public class OriEntity {

    public static final String TABLE_NAME = "orientation";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ORI_X = "oriX";
    public static final String COLUMN_ORI_Y = "oriY";
    public static final String COLUMN_ORI_Z = "oriZ";
    public static final String COLUMN_ORI_W = "oriW";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_TIME)
    private long time;

    @ColumnInfo(name = COLUMN_ORI_X)
    private float oriX;

    @ColumnInfo(name = COLUMN_ORI_Y)
    private float oriY;

    @ColumnInfo(name = COLUMN_ORI_Z)
    private float oriZ;

    @ColumnInfo(name = COLUMN_ORI_W)
    private float oriW;

    public OriEntity(float oriX, float oriY, float oriZ, float oriW) {
        this.time = System.currentTimeMillis();
        this.oriX = oriX;
        this.oriY = oriY;
        this.oriZ = oriZ;
        this.oriW = oriW;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getOriX() {
        return oriX;
    }

    public void setOriX(float oriX) {
        this.oriX = oriX;
    }

    public float getOriY() {
        return oriY;
    }

    public void setOriY(float oriY) {
        this.oriY = oriY;
    }

    public float getOriZ() {
        return oriZ;
    }

    public void setOriZ(float oriZ) {
        this.oriZ = oriZ;
    }

    public float getOriW() {
        return oriW;
    }

    public void setOriW(float oriW) {
        this.oriW = oriW;
    }

}
