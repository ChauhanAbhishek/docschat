package com.example.myapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface ChatDao {

    @Insert(onConflict = IGNORE)
    long insertChat(Chat chat);

    @Query("UPDATE chats SET sent_to_server = 1  where pk = :pk")
    int updateChatAsSent(long pk);

    @Query("SELECT * FROM chats where pk>0 order by timestamp ASC")
    LiveData<List<Chat>> getAllChats();

    @Query("SELECT * FROM chats where sent_to_server=0")
    List<Chat> getAllUnsentChats();

}