package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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