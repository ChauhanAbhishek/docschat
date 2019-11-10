package com.example.myapplication.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chats")
public class Chat {

    public static int MINE=0 , OTHERS=1;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "pk")
    private long pk;

    @ColumnInfo(name = "text")
    String text;

    @ColumnInfo(name = "type")
    int type;

    @ColumnInfo(name = "sent_to_server")
    boolean sentToServer;

    @ColumnInfo(name = "timestamp")
    int timeStamp;

    public Chat(String text, int type, boolean sentToServer, int timeStamp) {
        this.text = text;
        this.type = type;
        this.sentToServer = sentToServer;
        this.timeStamp = timeStamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSentToServer() {
        return sentToServer;
    }

    public void setSentToServer(boolean sentToServer) {
        this.sentToServer = sentToServer;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }
}
