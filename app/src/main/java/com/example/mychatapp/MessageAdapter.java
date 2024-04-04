package com.example.mychatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;
    private Context context;
    private List<MessageModel> messageModelList = new ArrayList<>();

    public MessageAdapter(Context context){
        this.context = context;
    }

    public void add(MessageModel messageModel){
        messageModelList.add(messageModel);
        notifyDataSetChanged();
    }
    public void clear(){
        messageModelList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if(viewType==VIEW_TYPE_SENT){
            View view = inflater.inflate(R.layout.message_row_sent,parent ,false);
            return new MessageAdapter.MyViewHolder(view);
        }
        else{
            View view = inflater.inflate(R.layout.message_row_received,parent ,false);
            return new MessageAdapter.MyViewHolder(view);
        }
    }


    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        MessageModel messageModel = messageModelList.get(position);

        if(messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid())){
            holder.textViewSendMessage.setText(messageModel.getMessage());
        }
        else holder.textViewReceivedMessage.setText(messageModel.getMessage());

    }

    @Override
    public int getItemViewType(int position) {
        if(messageModelList.get(position).getSenderId().equals(FirebaseAuth.getInstance().getUid())){
            return VIEW_TYPE_SENT;
        }
        else{
            return VIEW_TYPE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public List<MessageModel> getMessageModelList(){
        return messageModelList;
    }

    public void addAll(List<MessageModel> messages) {
        messageModelList.addAll(messages);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewSendMessage, textViewReceivedMessage;
        public MyViewHolder(View itemView){
            super(itemView);
            textViewSendMessage = itemView.findViewById(R.id.textViewSendMessage);
            textViewReceivedMessage = itemView.findViewById(R.id.textViewReceivedMessage);
        }
    }
}
