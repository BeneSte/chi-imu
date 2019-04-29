package com.example.myapplication.daos;


import com.example.myapplication.entities.ActivityEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM " + ActivityEntity.TABLE_NAME + " ORDER BY " + ActivityEntity.COLUMN_ID + " ASC LIMIT :limit")
    List<ActivityEntity> getActivityForSync(int limit);

    @Insert
    void insertActivity(ActivityEntity activityEntity);

    @Insert
    void insertActivities(List<ActivityEntity> activityEntities);

    @Delete
    void deleteActivity(ActivityEntity activityEntity);

    @Delete
    void deleteActivities(List<ActivityEntity> activityEntities);

    @Query("SELECT COUNT(*) FROM " + ActivityEntity.TABLE_NAME)
    int getCount();
}
