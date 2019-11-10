package com.example.myapplication;

import android.app.Activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //List<ChatItem> chatItemList = new ArrayList<>();
    ChatAdapter chatAdapter ;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView chatRecyclerView = findViewById(R.id.chat_recyclerView);
        editText = findViewById(R.id.edit_text);
        TextView sendText = findViewById(R.id.send_button);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter();
        chatRecyclerView.setAdapter(chatAdapter);


        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                sendMessage(editText.getText().toString());
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
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try
                        {
                            Log.d("cnrr",response.toString());
                            if(response.getInt("success")==1)
                            {
                                chatAdapter.appendChats(new ChatItem(message,ChatItem.MINE));
                                chatAdapter.appendChats(new ChatItem( response.getJSONObject("message").getString("message"),ChatItem.OTHERS));
                                editText.setText("");

                            }
                        }
                        catch (JSONException e)
                        {

                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(MainActivity.this,"Connection error",Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
