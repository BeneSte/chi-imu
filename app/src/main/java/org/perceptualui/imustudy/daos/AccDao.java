package org.perceptualui.imustudy.daos;

import org.perceptualui.imustudy.entities.AccEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AccDao {

    @Query("SELECT * FROM " + AccEntity.TABLE_NAME + " ORDER BY " + AccEntity.COLUMN_ID + " ASC LIMIT :limit")
    List<AccEntity> getAccForSync(int limit);

    @Insert
    void insertAcc(AccEntity accEntity);

    @Insert
    void insertAccs(List<AccEntity> accEntities);

    @Delete
    void deleteAcc(AccEntity accEntity);

    @Delete
    void deleteAccs(List<AccEntity> accEntities);

    @Query("SELECT COUNT(*) FROM " + AccEntity.TABLE_NAME)
    int getCount();
}
