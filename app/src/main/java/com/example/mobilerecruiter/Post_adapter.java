package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Post_adapter extends RecyclerView.Adapter<Post_adapter.MyViewHolder> {
    ArrayList<Post> post;
    ArrayList<Skill> skills;
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
        NetworkService.getInstance()
                .getJSONApi()
                .getSkillsById(post.get(position).getId())
                .enqueue(new Callback<List<Skill>>() {
                    @Override
                    public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                        assert response.body() != null;
                        skills = new ArrayList<>(response.body());
                        StringBuilder description= new StringBuilder();
                        for(int i=0;i<skills.size();i++){
                            description.append(skills.get(i).getTag()).append(" ");
                        }
                        myViewHolder.post_skills.setText(description.toString());

                    }

                    @Override
                    public void onFailure(Call<List<Skill>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
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
