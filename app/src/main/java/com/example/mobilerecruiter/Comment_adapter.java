package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Comment_adapter extends RecyclerView.Adapter<Comment_adapter.MyViewHolder> {
    ArrayList<Comment> comment;
    public Comment_adapter(ArrayList<Comment> comment){this.comment=comment;}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_info,viewGroup,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Comment_adapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.username.setText(comment.get(position).getUsername());
        myViewHolder.description.setText(comment.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return comment.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView username,description,experience;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.comment_info_username);
            description=itemView.findViewById(R.id.comment_info_description);
        }
    }
}
