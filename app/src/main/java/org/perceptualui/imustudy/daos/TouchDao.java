package org.perceptualui.imustudy.daos;

import org.perceptualui.imustudy.entities.TouchEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TouchDao{

    @Query("SELECT * FROM " + TouchEntity.TABLE_NAME + " ORDER BY " + TouchEntity.COLUMN_ID + " ASC LIMIT :limit")
    List<TouchEntity> getTouchesForSync(int limit);

    @Insert
    void insertTouch(TouchEntity touchEntity);

    @Insert
    void insertTouches(List<TouchEntity> touchEntities);

    @Delete
    void deleteTouch(TouchEntity touchEntity);

    @Delete
    void deleteTouches(List<TouchEntity> touchEntities);

    @Query("SELECT COUNT(*) FROM " + TouchEntity.TABLE_NAME)
    int getCount();
}
