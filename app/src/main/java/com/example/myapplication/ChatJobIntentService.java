package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.myapplication.db.Chat;
import com.example.myapplication.db.ChatDao;
import com.example.myapplication.db.ChatDatabase;

import org.json.JSONException;
import org.json.JSONObject;

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

        for(Chat chat : chatDao.getAllUnsentChats())
        {
            if(chat.isSentToServer()==0)
            {
                sendMessage(chat.getText(),chat.getPk());
            }
        }
    }

    private void sendMessage(final String message,long pk) {
        AndroidNetworking.get(" https://www.personalityforge.com/api/chat/?apiKey=6nt5d1nJHkqbkphe&chatBotID=63906&externalID=chirag1")
                .addPathParameter("message", message)
                .addQueryParameter("message", message)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse( JSONObject response) {
                        // do anything with response
                        try
                        {
                            Log.d("cnrr",response.toString());

                            final String reply = response.getJSONObject("message").getString("message");
                            if(response.getInt("success")==1)
                            {
                                // chatAdapter.appendChats(new Chat(message, Chat.MINE,true, (int)(System.currentTimeMillis() / 1000) ));
                                //   chatAdapter.appendChats(new Chat( response.getJSONObject("message").getString("message"), Chat.OTHERS,true, (int)(System.currentTimeMillis() / 1000) ));

                                updateChat(new Chat(message, Chat.MINE,1, (int)(System.currentTimeMillis() / 1000) ));
                                saveChat(new Chat(reply  , Chat.OTHERS,1, (int)(System.currentTimeMillis() / 1000) ));

                            }
                        }
                        catch (JSONException e)
                        {

                        }
                    }
                    @Override
                    public void onError(ANError error) {


                    }
                });


    }

    public void updateChat(final Chat chat)
    {
        mDiskIO.execute(new Runnable() {
            @Override
            public void run() {
                ChatDatabase.getInstance(ChatJobIntentService.this).getChatDao().updateChatAsSent(chat.getPk());
            }
        });
    }

    public void saveChat(Chat chat)
    {
        mDiskIO.execute(new Runnable() {
            @Override
            public void run() {
                ChatDatabase.getInstance(ChatJobIntentService.this).getChatDao().insertChat(chat);
            }
        });
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
