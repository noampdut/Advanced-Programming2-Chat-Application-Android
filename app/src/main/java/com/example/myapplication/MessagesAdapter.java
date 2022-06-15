package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    List<Message> messages;
    //public static final int MESSAGE_TYPE_IN = 1;
    //public static final int MESSAGE_TYPE_OUT = 2;

    public MessagesAdapter(Context context, List<Message> list) { // you can pass other parameters in constructor
        this.context = context;
        this.messages = list;
    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        TextView contentTV,dateTV;
        MessageInViewHolder(final View itemView) {
            super(itemView);
            contentTV = itemView.findViewById(R.id.content_text);
            dateTV = itemView.findViewById(R.id.date_text);
        }
        void bind(int position) {
            Message message = messages.get(position);
            contentTV.setText(message.getContent());
            dateTV.setText(message.getCreated());
        }
    }

    private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        TextView contentTV,dateTV;
        MessageOutViewHolder(final View itemView) {
            super(itemView);
            contentTV = itemView.findViewById(R.id.content_text);
            dateTV = itemView.findViewById(R.id.date_text);
        }
        void bind(int position) {
            Message message = messages.get(position);
            contentTV.setText(message.getContent());
            dateTV.setText(message.getCreated());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.outcoming_message, parent, false));
        }
        return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.incoming_message, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (messages.get(position).getSent()) {
            ((MessageInViewHolder) holder).bind(position);
        } else {
            ((MessageOutViewHolder) holder).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSent()){
            return 1;
        }
        return 0;
    }
}
