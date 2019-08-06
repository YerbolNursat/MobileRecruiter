package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.WINDOW_SERVICE;
import static android.widget.PopupWindow.INPUT_METHOD_NEEDED;

public class VacancyPostAdapter extends RecyclerView.Adapter<VacancyPostAdapter.MyViewHolder> {
    SearchView sv;
    private RecyclerView rv;
    private ArrayList<Post> post;
    ViewGroup viewGroup;
    View view;
    public VacancyPostAdapter(ArrayList<Post> post){this.post=post;}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vacancy_post_info,viewGroup,false);
        this.viewGroup=viewGroup;
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.name_surname.setText(post.get(position).getF_name()+" "+post.get(position).getL_name());
        myViewHolder.name_surname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Post_page.class);
                intent.putExtra("id",String.valueOf(post.get(position).getId()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return post.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name_surname;
        ImageButton like,dislike;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_surname=itemView.findViewById(R.id.vacancy_post_info_f_name_and_l_name);
            like=itemView.findViewById(R.id.vacancy_post_info_like);
            dislike=itemView.findViewById(R.id.vacancy_post_info_dislike);


        }
    }

}
