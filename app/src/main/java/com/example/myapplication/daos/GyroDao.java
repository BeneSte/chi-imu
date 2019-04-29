package com.example.myapplication.daos;

import com.example.myapplication.entities.GyroEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface GyroDao{

    @Query("SELECT * FROM " + GyroEntity.TABLE_NAME + " ORDER BY " + GyroEntity.COLUMN_ID + " ASC LIMIT :limit")
    List<GyroEntity> getGyroForSync(int limit);

    @Insert
    void insertGyro(GyroEntity oriEntity);

    @Insert
    void insertGyros(List<GyroEntity> gyroEntities);

    @Delete
    void deleteGyro(GyroEntity oriEntity);

    @Delete
    void deleteGyros(List<GyroEntity> gyroEntities);

    @Query("SELECT COUNT(*) FROM " + GyroEntity.TABLE_NAME)
    int getCount();
}
