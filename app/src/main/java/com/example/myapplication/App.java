package com.example.myapplication;

import com.evernote.android.job.JobManager;
import com.example.myapplication.misc.Const;

import android.app.Application;


public class App extends Application {

    public void onCreate() {
        super.onCreate();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JobManager.create(App.this).addJobCreator(new Creator());
                    SyncJob.doSync(getApplicationContext());
                }catch (Exception e){
                    if (Const.DEBUG) e.printStackTrace();
                }
            }
        }).start();


    }


}
