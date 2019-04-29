package com.example.myapplication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.example.myapplication.entities.AccEntity;
import com.example.myapplication.entities.ActivityEntity;
import com.example.myapplication.entities.GyroEntity;
import com.example.myapplication.entities.OriEntity;
import com.example.myapplication.entities.TouchEntity;
import com.example.myapplication.misc.Const;
import com.example.myapplication.misc.NetworkUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.room.Room;

public class SyncJob extends Job {

    public static final String TAG = "sync_tag";

    public static final int LIMIT = 20;

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        if(!NetworkUtil.isNetworkAvailable(getContext())){
            return Result.FAILURE;
        }
        doSync();
        return Result.SUCCESS;
    }


    public static void scheduleJob(){
        if (Const.DEBUG) Log.i(TAG, "Schedule job: " + TAG);
        new JobRequest.Builder(SyncJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(90))
                .setRequiredNetworkType(JobRequest.NetworkType.METERED)
                .setRequiresBatteryNotLow(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();

    }

    private void doSync() {
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, Const.DB_STUDY).build();
        List<AccEntity> accEntities = db.accDao().getAccForSync(LIMIT);
        List<GyroEntity> gyroEntities = db.gyroDao().getGyroForSync(LIMIT);
        List<OriEntity> oriEntities = db.oriDao().getOrisForSync(LIMIT);
        List<TouchEntity> touchEntities = db.touchDao().getTouchesForSync(LIMIT);
        List<ActivityEntity> activityEntities = db.activityDao().getActivityForSync(LIMIT);

    }


}
