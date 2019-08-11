package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class VacancyPostAdapter extends RecyclerSwipeAdapter<VacancyPostAdapter.MyViewHolder> {
    private ArrayList<Post> post;
    public VacancyPostAdapter(ArrayList<Post> post){this.post=post;}
    private SharedPreferences preferences;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vacancy_post_info,viewGroup,false);
        preferences = Objects.requireNonNull(viewGroup.getContext()).getSharedPreferences("myPrefs", MODE_PRIVATE);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final VacancyPostAdapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.name_surname.setText(post.get(position).getF_name()+" "+post.get(position).getL_name());

        myViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        myViewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Post_page.class);
                intent.putExtra("id",String.valueOf(post.get(position).getId()));
                view.getContext().startActivity(intent);
            }
        });

        if(preferences.getBoolean("is_admin",false)){
            if(post.get(position).getPassed_customer()==0){
                myViewHolder.status.setText("В ожиданий");
            }else {
                myViewHolder.status.setText("Прошел");
            }
        }else {
            if(post.get(position).getPassed_customer()==1){
                myViewHolder.status.setText("Прошел");
            }else {
                myViewHolder.status.setVisibility(View.GONE);
                myViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, myViewHolder.swipeLayout.findViewById(R.id.linear_layout));
            }
        }


        myViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                 NetworkService.getInstance()
                         .getJSONApi()
                         .putPost(post.get(position).getId(),post.get(position).getF_name(),post.get(position).getL_name(),post.get(position).getMail(),
                                 post.get(position).getTelephon_number(),post.get(position).getCv_file_name(),post.get(position).getVacancy_id(),
                                 1,1,0,0,null)
                         .enqueue(new Callback<Void>() {
                             @Override
                             public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(view.getContext(), "You accepted candidate", Toast.LENGTH_SHORT).show();
                                    }
                             }

                             @Override
                             public void onFailure(Call<Void> call, Throwable t) {
                                 Log.d("Error", t.toString());
                                 Toast.makeText(view.getContext(), "Something wrong...", Toast.LENGTH_SHORT).show();
                             }
                         });

            }
        });
        myViewHolder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                NetworkService.getInstance()
                        .getJSONApi()
                        .putPost(post.get(position).getId(),post.get(position).getF_name(),post.get(position).getL_name(),post.get(position).getMail(),
                                post.get(position).getTelephon_number(),post.get(position).getCv_file_name(),1,
                                0,0,0,0,null)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(view.getContext(), "You declined candidate", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("Error", t.toString());
                                Toast.makeText(view.getContext(), "Something wrong...", Toast.LENGTH_SHORT).show();
                            }
                        });

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
        TextView name_surname,status,like,dislike;
        public SwipeLayout swipeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout=itemView.findViewById(R.id.vacancy_post_swipe);
            name_surname=itemView.findViewById(R.id.vacancy_post_info_f_name_and_l_name);
            like=itemView.findViewById(R.id.vacancy_post_like);
            dislike=itemView.findViewById(R.id.vacancy_post_dislike);
            status=itemView.findViewById(R.id.vacancy_post_info_status);
        }
    }

}
