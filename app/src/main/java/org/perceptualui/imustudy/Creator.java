package org.perceptualui.imustudy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class Creator implements JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        try {
            switch (tag) {
                case SyncJob.TAG:
                    return new SyncJob();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
