package org.perceptualui.imustudy;

import androidx.annotation.NonNull;

import android.content.Context;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import org.perceptualui.imustudy.entities.AccEntity;
import org.perceptualui.imustudy.entities.ActivityEntity;
import org.perceptualui.imustudy.entities.GyroEntity;
import org.perceptualui.imustudy.entities.KeyboardEntity;
import org.perceptualui.imustudy.entities.OriEntity;
import org.perceptualui.imustudy.entities.TouchEntity;
import org.perceptualui.imustudy.entities.interfaces.IJsonConvertable;
import org.perceptualui.imustudy.misc.Const;
import org.perceptualui.imustudy.misc.NetworkUtil;
import org.perceptualui.imustudy.tasks.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public final int jobRounds = 1000;

    static List<AccEntity> accEntities;
    static List<GyroEntity> gyroEntities;
    static List<OriEntity> oriEntities;
    static List<TouchEntity> touchEntities;
    static List<ActivityEntity> activityEntities;
    static List<KeyboardEntity> keyboardEntities;


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
                .setRequiredNetworkType(JobRequest.NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }

    public void doSync(Context context){
        Log.d(TAG, "starting sync");

        AppDatabase db = AppDatabase.getInstance(context);
        for (int i = 0; i < jobRounds; i++) {
            Log.d(TAG, "Run: "+i);
            accEntities = db.accDao().getAccForSync(LIMIT);
            gyroEntities = db.gyroDao().getGyroForSync(LIMIT);
            oriEntities = db.oriDao().getOrisForSync(LIMIT);
            touchEntities = db.touchDao().getTouchesForSync(LIMIT);
            activityEntities = db.activityDao().getActivityForSync(LIMIT);
            keyboardEntities = db.keyboardDao().getKeyboardsForSync(LIMIT);


            if (accEntities.isEmpty() && gyroEntities.isEmpty() && oriEntities.isEmpty() && touchEntities.isEmpty() && activityEntities.isEmpty() && keyboardEntities.isEmpty()) {
                Log.d(TAG, "entities empty");
                return;
            }

            List myList = new ArrayList();

            JSONObject syncJson = new JSONObject();

            Log.d(TAG, "filling json object");

            addStringToJsonObject(syncJson, "uuid", UUID.getUUID(context));
            addListToJsonObject(syncJson, "accEntities", accEntities);
            addListToJsonObject(syncJson, "gyroEntities", gyroEntities);
            addListToJsonObject(syncJson, "oriEntities", oriEntities);
            addListToJsonObject(syncJson, "touchEntities", touchEntities);
            addTestListToJsonObject(syncJson, "activityEntities", myList);

            Log.d(TAG, "filled json object");

            OkHttpClient client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(false)
                    .build();
            RequestBody body = RequestBody.create(JSON, syncJson.toString());
            Request request = new Request.Builder()
                    .url(Const.SERVER_URL)
                    .post(body)
                    .build();
            Response response = null;
            Log.d(TAG, "trying to request");
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                if (Const.DEBUG) e.printStackTrace();
            }
            Log.d(TAG, "tried requesting");
            if (response != null && response.isSuccessful()) {
                Log.d(TAG, "response successful");
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseString = null;
                    try {
                        responseString = responseBody.string();
                    } catch (IOException e) {
                        if (Const.DEBUG) e.printStackTrace();
                    }
                    if (Const.DEBUG) {
                        Log.d(TAG, "Response: " + responseString);
                    }
                    try {
                        deleteFields(db);
                        Log.d(TAG, "Deleting fields");
                    } catch (Exception e) {
                        if (Const.DEBUG) e.printStackTrace();
                    }

                }
            }
        }
    }

    private static void deleteFields(AppDatabase db) {
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

    private static void addTestListToJsonObject(JSONObject syncJson, String key, List convertableList) {
        try {
            syncJson.put(key, listToJsonArray(convertableList));
        } catch (Exception e) {
            System.err.println("Error when trying to convert " + key + " to json array.");
            e.printStackTrace();
        }
    }

    private static void addStringToJsonObject(JSONObject syncJson, String key, String value){
        try {
            syncJson.put(key, value);
        } catch (Exception e) {
            System.err.println("Error when trying to convert " + key + " to json array.");
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
