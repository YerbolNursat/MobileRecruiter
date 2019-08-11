package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class is_deployed_adapter extends RecyclerSwipeAdapter<is_deployed_adapter.MyViewHolder> {
    private ArrayList<Post> post;
    View view;
    private SharedPreferences preferences;

    public is_deployed_adapter(ArrayList<Post> post) {
        this.post = post;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.is_deployed_info, viewGroup, false);
        preferences = Objects.requireNonNull(viewGroup.getContext()).getSharedPreferences("myPrefs", MODE_PRIVATE);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

        myViewHolder.name_surname.setText(post.get(position).getF_name() + " " + post.get(position).getL_name());
        myViewHolder.post_skills.setText("");
        for (int i = 0; i < post.get(position).getSkills().size(); i++) {
            myViewHolder.post_skills.setText(myViewHolder.post_skills.getText() + " " +
                    post.get(position).getSkills().get(i));
        }
        if(preferences.getBoolean("is_admin",false)){
            myViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, myViewHolder.swipeLayout.findViewById(R.id.bottom_wraper));
        }else {
            myViewHolder.swipeLayout.findViewById(R.id.bottom_wraper).setVisibility(View.GONE);
            myViewHolder.decline.setVisibility(View.GONE);
            myViewHolder.accept.setVisibility(View.GONE);
        }
        myViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        myViewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Post_page.class);
                intent.putExtra("id", String.valueOf(post.get(position).getId()));
                view.getContext().startActivity(intent);
            }
        });
        myViewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                NetworkService.getInstance()
                        .getJSONApi()
                        .putPost(post.get(position).getId(),post.get(position).getF_name(),post.get(position).getL_name(),post.get(position).getMail(),
                                post.get(position).getTelephon_number(),post.get(position).getCv_file_name(),post.get(position).getVacancy_id(),
                                1,1,1,1,null)
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
        myViewHolder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (preferences.getBoolean("is_admin", false)) {
                    NetworkService.getInstance()
                            .getJSONApi()
                            .putPost(post.get(position).getId(),post.get(position).getF_name(),post.get(position).getL_name(),post.get(position).getMail(),
                                    post.get(position).getTelephon_number(),post.get(position).getCv_file_name(),1,
                                    0,0,0,0,null)
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()){
                                        mItemManger.removeShownLayouts(myViewHolder.swipeLayout);
                                        post.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, post.size());
                                        mItemManger.closeAllItems();
                                        Toast.makeText(view.getContext(), "You decline candidate", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.d("Error", t.toString());
                                    Toast.makeText(view.getContext(), "Something wrong...", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(view.getContext(), "You don't have access", Toast.LENGTH_SHORT).show();
                }
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
        return R.id.is_deployed_info_swipe;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_surname, post_skills, accept, decline;
        SwipeLayout swipeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.is_deployed_info_swipe);
            name_surname = itemView.findViewById(R.id.is_deployed_f_name_and_l_name);
            post_skills = itemView.findViewById(R.id.is_deployed_skills);
            accept = itemView.findViewById(R.id.is_deployed_accept);
            decline = itemView.findViewById(R.id.is_deployed_decline);
        }
    }
}