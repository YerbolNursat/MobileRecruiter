package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
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

public class Post_adapter extends RecyclerView.Adapter<Post_adapter.MyViewHolder> {
    SearchView sv;
    private RecyclerView rv;
    private ArrayList<Post> post;
    private ArrayList<Vacancy> events;
    ViewGroup viewGroup;
    View view;
    public Post_adapter(ArrayList<Post> post){this.post=post;}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_info,viewGroup,false);
        this.viewGroup=viewGroup;
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

        myViewHolder.name_surname.setText(post.get(position).getF_name()+" "+post.get(position).getL_name());
        myViewHolder.post_skills.setText("");
        for (int i=0;i<post.get(position).getSkills().size();i++){
            myViewHolder.post_skills.setText(myViewHolder.post_skills.getText()+" "+
                    post.get(position).getSkills().get(i));
        }
        myViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopUp(v,position);
            }
        });
        myViewHolder.name_surname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Post_page.class);
                intent.putExtra("id",String.valueOf(post.get(position).getId()));
                v.getContext().startActivity(intent);
            }
        });
        myViewHolder.post_skills.setOnClickListener(new View.OnClickListener() {
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
        TextView name_surname,post_skills;
        ImageButton imageButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_surname=itemView.findViewById(R.id.post_f_name_and_l_name);
            post_skills=itemView.findViewById(R.id.post_skills);
            imageButton=itemView.findViewById(R.id.post_info_image_button);
        }
    }



    public void ShowPopUp(final View v, final int i){
        WindowManager windowManager = (WindowManager) v.getContext().getSystemService(WINDOW_SERVICE);

        int height = (int) (windowManager.getDefaultDisplay().getHeight()*0.6);
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.send_pop_up,viewGroup,false);
        GradientDrawable drawable = (GradientDrawable) view.getResources().getDrawable(R.drawable.popup_shape);
        drawable.setColor(Color.parseColor("#34495e"));
        view.setBackground(drawable);

        final PopupWindow myDialog = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,height,true);
        rv=view.findViewById(R.id.send_pop_up_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv.setHasFixedSize(true);
        sv=view.findViewById(R.id.send_pop_up_search);
        NetworkService.getInstance()
                .getJSONApi()
                .getVacancies()
                .enqueue(new Callback<List<Vacancy>>() {
                    @Override
                    public void onResponse(Call<List<Vacancy>> call, Response<List<Vacancy>> response) {
                        assert response.body() != null;
                        events = new ArrayList<>(response.body());
                        Vacancy_adapter adapter=new Vacancy_adapter(events);
                        rv.setAdapter(adapter);
                    }
                    @Override
                    public void onFailure(Call<List<Vacancy>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        rv.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), rv, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                put(events.get(position).getId(),post.get(i));
                myDialog.dismiss();
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        myDialog.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        sv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                myDialog.setInputMethodMode(INPUT_METHOD_NEEDED);
                myDialog.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
                myDialog.showAtLocation(v, Gravity.CENTER, 0, 0);

            }
        });
    }
    public void put(int id, Post post){
        new NetworkService().
                getJSONApi().
                putPost(post.getId(),post.getF_name(),post.getL_name(),post.getMail(),post.getTelephon_number(),post.getCv_file_name(),id).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.e("Success", "Success" );
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("Error", "error" );
                    }
                });
    }
}
