package com.example.myapplication.entities;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = ActivityEntity.TABLE_NAME)
public class ActivityEntity {

    public static final String TABLE_NAME = "activity";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ACTIVITY = "activityType";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_TIME)
    private long time;

    @ColumnInfo(name = COLUMN_ACTIVITY)
    private int type;


    public ActivityEntity(int type){
        this.time = System.currentTimeMillis();
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
