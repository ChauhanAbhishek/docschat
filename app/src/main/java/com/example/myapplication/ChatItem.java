package com.example.myapplication;



public class ChatItem {

    public static int MINE=0 , OTHERS=1;

    String text;
    int type;

    public ChatItem(String text, int type) {
        this.text = text;
        this.type = type;
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
}
