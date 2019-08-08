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
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.WINDOW_SERVICE;
import static android.widget.PopupWindow.INPUT_METHOD_NEEDED;

public class VacancyPostAdapter extends RecyclerSwipeAdapter<VacancyPostAdapter.MyViewHolder> {
    private ArrayList<Post> post;
    public VacancyPostAdapter(ArrayList<Post> post){this.post=post;}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vacancy_post_info,viewGroup,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.name_surname.setText(post.get(position).getF_name()+" "+post.get(position).getL_name());

        myViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        myViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, myViewHolder.swipeLayout.findViewById(R.id.bottom_wraper));

        myViewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Post_page.class);
                intent.putExtra("id",String.valueOf(post.get(position).getId()));
                view.getContext().startActivity(intent);
            }
        });


        myViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "You disliked it", Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Toast.makeText(view.getContext(), "You liked it", Toast.LENGTH_SHORT).show();
            }
        });
        mItemManger.bindView(myViewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.vacancy_post_swipe;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name_surname,skill,like,dislike;
        SwipeLayout swipeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout=itemView.findViewById(R.id.vacancy_post_swipe);
            name_surname=itemView.findViewById(R.id.vacancy_post_info_f_name_and_l_name);
            skill=itemView.findViewById(R.id.vacancy_post_skills);
            like=itemView.findViewById(R.id.vacancy_post_like);
            dislike=itemView.findViewById(R.id.vacancy_post_dislike);

        }
    }

}
