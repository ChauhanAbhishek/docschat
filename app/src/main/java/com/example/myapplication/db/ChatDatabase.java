package com.example.myapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Chat.class}, version = 1)
public abstract class ChatDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "room_yq_db";

    private static ChatDatabase instance;

    public static ChatDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ChatDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract ChatDao getChatDao();

}

