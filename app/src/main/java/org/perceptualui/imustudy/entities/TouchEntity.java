package org.perceptualui.imustudy.entities;

import android.provider.BaseColumns;

import org.perceptualui.imustudy.entities.interfaces.IJsonConvertable;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = TouchEntity.TABLE_NAME)
public class TouchEntity implements IJsonConvertable {

    public static final String TABLE_NAME = "touches";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_FINGER_ID = "fingerID";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_X = "x";
    public static final String COLUMN_Y = "y";
    public static final String COLUMN_PRESSURE = "pressure";

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
    private float pressure;


    public TouchEntity(long time, int fingerID, String type, int xCoord, int yCoord, float pressure){
        this.time = time;
        this.fingerID = fingerID;
        this.type = type;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.pressure = pressure;
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

    public int getXCoord() {
        return xCoord;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure){
        this.pressure = pressure;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("time",getTime());
            jsonObject.put("fingerID", getFingerID());
            jsonObject.put("type", getType());
            jsonObject.put("touchX", getXCoord());
            jsonObject.put("touchY", getYCoord());
            jsonObject.put("pressure", getPressure());
        } catch (JSONException e) {
            throw new RuntimeException("Error when converting to JSON");
        }

        return jsonObject;
    }
}
