package org.perceptualui.imustudy;

import android.content.Context;

import org.perceptualui.imustudy.daos.AccDao;
import org.perceptualui.imustudy.daos.ActivityDao;
import org.perceptualui.imustudy.daos.GyroDao;
import org.perceptualui.imustudy.daos.KeyboardDao;
import org.perceptualui.imustudy.daos.OriDao;
import org.perceptualui.imustudy.daos.TouchDao;
import org.perceptualui.imustudy.entities.AccEntity;
import org.perceptualui.imustudy.entities.ActivityEntity;
import org.perceptualui.imustudy.entities.GyroEntity;
import org.perceptualui.imustudy.entities.KeyboardEntity;
import org.perceptualui.imustudy.entities.OriEntity;
import org.perceptualui.imustudy.entities.TouchEntity;
import org.perceptualui.imustudy.misc.Const;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ActivityEntity.class, TouchEntity.class, AccEntity.class, GyroEntity.class, OriEntity.class, KeyboardEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public abstract ActivityDao activityDao();
    public abstract TouchDao touchDao();
    public abstract AccDao accDao();
    public abstract GyroDao gyroDao();
    public abstract OriDao oriDao();
    public abstract KeyboardDao keyboardDao();

    public static AppDatabase getInstance(Context context){
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Const.DB_STUDY).build();
        }
        return sInstance;
    }

}
