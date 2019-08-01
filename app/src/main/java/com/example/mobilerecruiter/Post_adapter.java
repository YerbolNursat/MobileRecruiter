package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Post_adapter extends RecyclerView.Adapter<Post_adapter.MyViewHolder> {
    ArrayList<Post> post;
    public Post_adapter(ArrayList<Post> post){this.post=post;}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_info,viewGroup,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.name_surname.setText(post.get(position).getF_name()+" "+post.get(position).getL_name());
        for (int i=0;i<post.get(position).getSkills().size();i++){
            myViewHolder.post_skills.setText(myViewHolder.post_skills.getText()+" "+
                    post.get(position).getSkills().get(i));
        }
    }

    @Override
    public int getItemCount() {
        return post.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name_surname,post_skills;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_surname=itemView.findViewById(R.id.post_f_name_and_l_name);
            post_skills=itemView.findViewById(R.id.post_skills);
        }
    }
}
