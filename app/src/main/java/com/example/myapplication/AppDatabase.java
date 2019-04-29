package com.example.myapplication;

import com.example.myapplication.daos.AccDao;
import com.example.myapplication.daos.ActivityDao;
import com.example.myapplication.daos.GyroDao;
import com.example.myapplication.daos.OriDao;
import com.example.myapplication.daos.TouchDao;
import com.example.myapplication.entities.AccEntity;
import com.example.myapplication.entities.ActivityEntity;
import com.example.myapplication.entities.GyroEntity;
import com.example.myapplication.entities.OriEntity;
import com.example.myapplication.entities.TouchEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ActivityEntity.class, TouchEntity.class, AccEntity.class, GyroEntity.class, OriEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ActivityDao activityDao();
    public abstract TouchDao touchDao();
    public abstract AccDao accDao();
    public abstract GyroDao gyroDao();
    public abstract OriDao oriDao();

}
