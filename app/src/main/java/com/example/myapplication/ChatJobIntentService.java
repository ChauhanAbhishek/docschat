package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.myapplication.db.ChatDao;
import com.example.myapplication.db.ChatDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatJobIntentService extends JobIntentService {

    private Executor mDiskIO;
    private ChatDao chatDao;


    @Override
    public void onCreate() {
        super.onCreate();
        mDiskIO = Executors.newSingleThreadExecutor();
        chatDao = ChatDatabase.getInstance(this).getChatDao();
    }

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
