package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //List<ChatItem> chatItemList = new ArrayList<>();
    ChatAdapter chatAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView chatRecyclerView = findViewById(R.id.chat_recyclerView);
        EditText editText = findViewById(R.id.edit_text);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter();

        chatRecyclerView.setAdapter(chatAdapter);


    }
}
