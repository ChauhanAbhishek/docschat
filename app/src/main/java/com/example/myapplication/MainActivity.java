package com.example.myapplication;

import android.app.Activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.myapplication.db.Chat;
import com.example.myapplication.db.ChatDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements ChatAdapter.ChatAdapterCallback {


    List<Chat> chatItemList = new ArrayList<>();
    ChatAdapter chatAdapter ;
    EditText editText;
    private Executor mDiskIO;
    RecyclerView chatRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Your Chats");
        }


         chatRecyclerView = findViewById(R.id.chat_recyclerView);
        editText = findViewById(R.id.edit_text);
        TextView sendText = findViewById(R.id.send_button);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this);
        chatRecyclerView.setAdapter(chatAdapter);


        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                sendMessage(editText.getText().toString());
            }


        });
        mDiskIO = Executors.newSingleThreadExecutor();
        subscribeObserver();
        sendAnyUnSentMessages();

    }

    @Override
    public void scrollToBottom() {
        Log.d("cnrr","scroll to " + (chatItemList.size()-1));
        chatRecyclerView.scrollToPosition(chatItemList.size()-1);
    }

    public void sendAnyUnSentMessages()
    {
        Intent serviceIntent = new Intent(MainActivity.this, ChatJobIntentService.class);
        ChatJobIntentService.enqueueWork(MainActivity.this,serviceIntent,123);
    }

    public void subscribeObserver()
    {
        ChatDatabase.getInstance(this).getChatDao().getAllChats().observe(this, new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> chats) {
                if(chats!=null)
                {                    chatItemList =chats;
                    chatAdapter.setChatList(chats);
                }
            }
        });
    }


    public  void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




    private void sendMessage(final String message) {
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

                                saveChat(new Chat(message, Chat.MINE,1, (int)(System.currentTimeMillis() / 1000) ));
                                saveChat(new Chat(reply  , Chat.OTHERS,1, (int)(System.currentTimeMillis() / 1000) ));

                                editText.setText("");

                            }
                        }
                        catch (JSONException e)
                        {
                           saveChat(new Chat(message, Chat.MINE,0, (int)(System.currentTimeMillis() / 1000) ));
                            editText.setText("");

                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(MainActivity.this,"Connection error",Toast.LENGTH_SHORT).show();
                        saveChat(new Chat(message, Chat.MINE,0, (int)(System.currentTimeMillis() / 1000) ));
                        editText.setText("");

                    }
                });


    }

    public void saveChat(final Chat chat)
    {
        mDiskIO.execute(new Runnable() {
            @Override
            public void run() {
                ChatDatabase.getInstance(MainActivity.this).getChatDao().insertChat(chat);
            }
        });
    }

    private BroadcastReceiver networkStateReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            Log.d("cnrr",networkInfo + "hi");

            if(networkInfo!=null&&networkInfo.isConnected())
            {
                Log.d("cnrr","enqueue work");
                sendAnyUnSentMessages();

            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        unregisterReceiver(networkStateReceiver);
        super.onPause();
    }


}
