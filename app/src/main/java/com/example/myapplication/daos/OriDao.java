package com.example.myapplication.daos;

import com.example.myapplication.entities.OriEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface OriDao {

    @Query("SELECT * FROM " + OriEntity.TABLE_NAME + " ORDER BY " + OriEntity.COLUMN_ID + " ASC LIMIT :limit")
    List<OriEntity> getOrisForSync(int limit);

    @Insert
    void insertOri(OriEntity oriEntity);

    @Insert
    void insertOris(List<OriEntity> oriEntities);

    @Delete
    void deleteOri(OriEntity oriEntity);

    @Delete
    void deleteOris(List<OriEntity> oriEntities);

    @Query("SELECT COUNT(*) FROM " + OriEntity.TABLE_NAME)
    int getCount();

}
