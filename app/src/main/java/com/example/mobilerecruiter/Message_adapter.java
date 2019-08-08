package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Message_adapter extends RecyclerView.Adapter<Message_adapter.MyViewHolder> {
    ArrayList<Message> messages;
    public Message_adapter(ArrayList<Message> messages){this.messages=messages;}
    @NonNull
    @Override
    public Message_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_info,viewGroup,false);
        return new Message_adapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final Message_adapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.from.setText(messages.get(position).getFrom());
        myViewHolder.date.setText(messages.get(position).getDate());
        myViewHolder.subject.setText(messages.get(position).getSubject());
        myViewHolder.filename.setText(messages.get(position).getFilename());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView from,date,subject,filename;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            from=itemView.findViewById(R.id.message_info_from);
            date=itemView.findViewById(R.id.message_date);
            subject=itemView.findViewById(R.id.message_subject);
            filename=itemView.findViewById(R.id.message_filename);

        }
    }
}
