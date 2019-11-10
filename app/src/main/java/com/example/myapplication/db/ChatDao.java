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

//    @Query("UPDATE drafts SET text = :text,updated_on = :updatedOn, is_dirty = :dirty  where pk = :pk")
//    Single<Integer> updateDraft(long pk,String text, int updatedOn,int dirty);

    @Query("SELECT * FROM chats where pk>0 order by timestamp ASC")
    LiveData<List<Chat>> getAllChats();

//    @Query("SELECT * FROM chats where sent_to_server=")
//    LiveData<List<Chat>> getAllChats();

}