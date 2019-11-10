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
        TextView chatText;

        public MineChatViewHolder(View itemView) {
            super(itemView);
            chatText = itemView.findViewById(R.id.chatText);
        }
    }

    public static class OthersChatViewHolder extends RecyclerView.ViewHolder {
        TextView chatText;

        public OthersChatViewHolder(View itemView) {
            super(itemView);
            chatText = itemView.findViewById(R.id.chatText);
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

        if(chatList.get(position).type==ChatItem.MINE)
        {
            ((MineChatViewHolder)holder).chatText.setText(chatList.get(position).text);
        }
        else if(chatList.get(position).type==ChatItem.OTHERS)
        {
            ((OthersChatViewHolder)holder).chatText.setText(chatList.get(position).text);
        }


    }

    public void appendChats(ChatItem chatItem)
    {
        this.chatList.add(chatItem);
        notifyDataSetChanged();
    }
}
