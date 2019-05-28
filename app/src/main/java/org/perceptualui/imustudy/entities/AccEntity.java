package org.perceptualui.imustudy.entities;

import android.provider.BaseColumns;

import org.perceptualui.imustudy.entities.interfaces.IJsonConvertable;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AccEntity.TABLE_NAME)
public class AccEntity implements IJsonConvertable {

    public static final String TABLE_NAME = "accelerometer";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ACC_X = "accX";
    public static final String COLUMN_ACC_Y = "accY";
    public static final String COLUMN_ACC_Z = "accZ";


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_TIME)
    private long time;

    @ColumnInfo(name = COLUMN_ACC_X)
    private float accX;

    @ColumnInfo(name = COLUMN_ACC_Y)
    private float accY;

    @ColumnInfo(name = COLUMN_ACC_Z)
    private float accZ;


    public AccEntity(float accX, float accY, float accZ){
        this.time = System.currentTimeMillis();
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getAccX() {
        return accX;
    }

    public void setAccX(float accX) {
        this.accX = accX;
    }

    public float getAccY() {
        return accY;
    }

    public void setAccY(float accY) {
        this.accY = accY;
    }

    public float getAccZ() {
        return accZ;
    }

    public void setAccZ(float accZ) {
        this.accZ = accZ;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("time",getTime());
            jsonObject.put("accX", getAccX());
            jsonObject.put("accY", getAccY());
            jsonObject.put("accZ", getAccZ());
        } catch (JSONException e) {
            throw new RuntimeException("Error when converting to JSON");
        }

        return jsonObject;
    }
}
