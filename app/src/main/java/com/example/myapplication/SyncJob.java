package com.example.myapplication;

import androidx.annotation.NonNull;

import android.content.Context;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.example.myapplication.entities.AccEntity;
import com.example.myapplication.entities.ActivityEntity;
import com.example.myapplication.entities.GyroEntity;
import com.example.myapplication.entities.OriEntity;
import com.example.myapplication.entities.TouchEntity;
import com.example.myapplication.entities.interfaces.IJsonConvertable;
import com.example.myapplication.misc.Const;
import com.example.myapplication.misc.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import androidx.room.Room;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SyncJob extends Job {

    public static final String TAG = "sync_tag";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final int LIMIT = 20;

    static List<AccEntity> accEntities;
    static List<GyroEntity> gyroEntities;
    static List<OriEntity> oriEntities;
    static List<TouchEntity> touchEntities;
    static List<ActivityEntity> activityEntities;


    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        if(!NetworkUtil.isNetworkAvailable(getContext())){
            Log.w("OnRunJob", "Lizard");
            return Result.FAILURE;
        }

        Log.w("OnRunJob", "No failure");
        //doSync();
        return Result.SUCCESS;
    }


    public static void scheduleJob(){
        if (Const.DEBUG) Log.i(TAG, "Schedule job: " + TAG);
        new JobRequest.Builder(SyncJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15))
                .setRequiredNetworkType(JobRequest.NetworkType.METERED)
                .setRequiresBatteryNotLow(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();

    }

    public static void doSync(Context context) {
        Log.d(TAG, "starting sync");
        AppDatabase db = AppDatabase.getInstance(context);
        accEntities = db.accDao().getAccForSync(LIMIT);
        gyroEntities = db.gyroDao().getGyroForSync(LIMIT);
        oriEntities = db.oriDao().getOrisForSync(LIMIT);
        touchEntities = db.touchDao().getTouchesForSync(LIMIT);
        activityEntities = db.activityDao().getActivityForSync(LIMIT);

        if(accEntities.isEmpty() && gyroEntities.isEmpty() && oriEntities.isEmpty() && touchEntities.isEmpty() && activityEntities.isEmpty()) {
            return;
        }

        JSONObject syncJson = new JSONObject();

        addListToJsonObject(syncJson, "accEntities", accEntities);
        addListToJsonObject(syncJson, "gyroEntities", gyroEntities);
        addListToJsonObject(syncJson, "oriEntities", oriEntities);
        addListToJsonObject(syncJson, "touchEntities", touchEntities);
        addListToJsonObject(syncJson, "activityEntities", activityEntities);

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .build();
        RequestBody body = RequestBody.create(JSON, syncJson.toString());
        Request request = new Request.Builder()
                .url(Const.SERVER_URL)
                .post(body)
                .build();
        Response response = null;
        Log.d(TAG, "response = zero, trying to request");
        try {
            response = client.newCall(request).execute();
        } catch (IOException e){
            if(Const.DEBUG) e.printStackTrace();
        }
        Log.d(TAG, "tried requesting");
        if(response != null && response.isSuccessful()) {
            Log.d(TAG, "response successful");
            ResponseBody responseBody = response.body();
            if(responseBody != null) {
                String responseString = null;
                try {
                    responseString = responseBody.string();
                } catch (IOException e) {
                    if(Const.DEBUG) e.printStackTrace();
                }
                if(Const.DEBUG) {
                    Log.d(TAG,"Response: " + responseString);
                }
                /*if("ok".equals(responseString)) {
                    try{
                        deleteFields(db);
                    }catch (Exception e) {
                        if (Const.DEBUG) e.printStackTrace();
                    }
                }*/
            }
        }
    }

    private void deleteFields(AppDatabase db) {
        db.accDao().deleteAccs(accEntities);
        db.gyroDao().deleteGyros(gyroEntities);
        db.oriDao().deleteOris(oriEntities);
        db.touchDao().deleteTouches(touchEntities);
        db.activityDao().deleteActivities(activityEntities);
    }

    private static void addListToJsonObject(JSONObject syncJson, String key, List<? extends IJsonConvertable> convertableList) {
        try {
            syncJson.put(key, listToJsonArray(convertableList));
        } catch (Exception e) {
            System.err.println("Error when trying to convert "+key+ " to json array.");
            e.printStackTrace();
        }
    }

    private static JSONArray listToJsonArray(List<? extends IJsonConvertable> convertableList) {
        return new JSONArray(
                convertableList
                .stream()
                .map(IJsonConvertable::toJson)
        .collect(Collectors.toList()));
    }



}
