package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import java.util.concurrent.Executor;

public class ChatJobIntentService extends JobIntentService {

    private Executor mDiskIO;

    public static void enqueueWork(Context context, Intent work, int jobId) {
        enqueueWork(context, ChatJobIntentService.class, jobId, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onStopCurrentWork() {
        return super.onStopCurrentWork();
    }
}
