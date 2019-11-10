package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.db.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Chat> chatList = new ArrayList<>();

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
        if(viewType== Chat.MINE)
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
        return chatList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(chatList.get(position).getType()== Chat.MINE)
        {
            ((MineChatViewHolder)holder).chatText.setText(chatList.get(position).getText());
        }
        else if(chatList.get(position).getType()== Chat.OTHERS)
        {
            ((OthersChatViewHolder)holder).chatText.setText(chatList.get(position).getText());
        }


    }

    public void setChatList(List<Chat> chatList)
    {
        this.chatList = chatList;
        notifyDataSetChanged();
    }
}
