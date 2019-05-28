package org.perceptualui.imustudy.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import org.perceptualui.imustudy.entities.KeyboardEntity;

import java.util.List;

@Dao
public interface KeyboardDao {
    @Query("SELECT * FROM " + KeyboardEntity.TABLE_NAME + " ORDER BY " + KeyboardEntity.COLUMN_ID + " ASC LIMIT :limit")
    List<KeyboardEntity> getKeyboardsForSync(int limit);

    @Insert
    void insertKeyboard(KeyboardEntity oriEntity);

    @Insert
    void insertKeyboards(List<KeyboardEntity> oriEntities);

    @Delete
    void deleteKeyboard(KeyboardEntity oriEntity);

    @Delete
    void deleteKeyboards(List<KeyboardEntity> oriEntities);

    @Query("SELECT COUNT(*) FROM " + KeyboardEntity.TABLE_NAME)
    int getCount();
}
