package org.perceptualui.imustudy;

import com.evernote.android.job.JobManager;
import org.perceptualui.imustudy.misc.Const;

import android.app.Application;


public class App extends Application {

    public void onCreate() {
        super.onCreate();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JobManager.create(App.this).addJobCreator(new Creator());
                    SyncJob.scheduleJob();
                }catch (Exception e){
                    if (Const.DEBUG) e.printStackTrace();
                }
            }
        }).start();


    }


}
