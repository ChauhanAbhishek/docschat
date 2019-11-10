package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.db.Chat;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Chat> chatList = new ArrayList<>();
    ChatAdapterCallback callback;

    public ChatAdapter(ChatAdapterCallback callback) {
        this.callback = callback;
    }

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

        Chat chat = chatList.get(position);

        if(chat.getType()== Chat.MINE)
        {
            ((MineChatViewHolder)holder).chatText.setText(chatList.get(position).getText());
            if(chat.isSentToServer()==0)
            {
                ((MineChatViewHolder)holder).chatText.setTextColor(Color.parseColor("#ff0000"));
            }
            else
            {
                ((MineChatViewHolder)holder).chatText.setTextColor(Color.parseColor("#000000"));
            }
        }
        else if(chat.getType()== Chat.OTHERS)
        {
            ((OthersChatViewHolder)holder).chatText.setText(chatList.get(position).getText());

//            if(chat.isSentToServer()==0)
//            {
//                ((OthersChatViewHolder)holder).chatText.setTextColor(Color.parseColor("#ff0000"));
//            }
//            else
//            {
//                ((OthersChatViewHolder)holder).chatText.setTextColor(Color.parseColor("#000000"));
//            }
        }



    }

    public void setChatList(List<Chat> chatList)
    {
        this.chatList = chatList;
        notifyDataSetChanged();
        callback.scrollToBottom();

    }

    public interface ChatAdapterCallback
    {
       public void  scrollToBottom();
    }
}
