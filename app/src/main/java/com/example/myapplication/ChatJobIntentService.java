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

        Log.d("cnrr","on handle");

        for(Chat chat : chatDao.getAllUnsentChats())
        {

            sendMessage(chat.getText(),chat.getPk());

        }
    }

    private void sendMessage(final String message, final long pk) {
        AndroidNetworking.get(" https://www.personalityforge.com/api/chat/?apiKey=6nt5d1nJHkqbkphe&chatBotID=63906&externalID=chirag1")
                .addPathParameter("message", message)
                .addQueryParameter("message", message)
                .setTag(pk)
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

                                updateChat(pk,reply);

                            }
                        }
                        catch (JSONException e)
                        {
                            Log.d("cnrr",e.toString());
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                        Log.d("cnrr",error.toString());
                    }
                });


    }

    public void updateChat(final long pk, final String message)
    {
        mDiskIO.execute(new Runnable() {
            @Override
            public void run() {
                boolean isChatUpdated = ChatDatabase.getInstance(ChatJobIntentService.this).getChatDao().getChatStatus(pk).size()==0;
                if(isChatUpdated)
                {
                    ChatDatabase.getInstance(ChatJobIntentService.this).getChatDao().insertChat(new Chat(message  , Chat.OTHERS,1, (int)(System.currentTimeMillis() / 1000) ));
                    ChatDatabase.getInstance(ChatJobIntentService.this).getChatDao().updateChatAsSent(pk);
                }

            }
        });
    }

    public void saveChat(final Chat chat)
    {
        mDiskIO.execute(new Runnable() {
            @Override
            public void run() {

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
