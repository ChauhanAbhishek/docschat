package com.example.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ChatItem> chatList = new ArrayList<>();

    public static class MineChatViewHolder extends RecyclerView.ViewHolder {
        TextView text1;

        public MineChatViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class OthersChatViewHolder extends RecyclerView.ViewHolder {
        TextView text1;

        public OthersChatViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ChatItem.MINE)
        {
            View p = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_row_mine, parent, false);
            return new MineChatViewHolder(p);
        }
        else
        {
            View p = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_row_other, parent, false);
            return new OthersChatViewHolder(p);
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chatList.get(position).type;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

    }

    public void appendChats(List<ChatItem> chatList)
    {
        this.chatList.addAll(chatList);
        notifyDataSetChanged();
    }
}
