package org.perceptualui.imustudy.entities;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
import org.perceptualui.imustudy.entities.interfaces.IJsonConvertable;

@Entity(tableName = KeyboardEntity.TABLE_NAME)
public class KeyboardEntity implements IJsonConvertable {

    public static final String TABLE_NAME = "keyboard";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_STATE = "state";



    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_TIME)
    private long time;

    @ColumnInfo(name = COLUMN_STATE)
    private boolean state;

    public KeyboardEntity(long time, boolean state) {
        this.time = time;
        this.state = state;
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

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("time",getTime());
            jsonObject.put("oriX", getState());
        } catch (JSONException e) {
            throw new RuntimeException("Error when converting to JSON");
        }

        return jsonObject;
    }
}
