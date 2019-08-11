package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import static android.content.Context.WINDOW_SERVICE;
import static android.widget.PopupWindow.INPUT_METHOD_NEEDED;

public class Post_adapter extends RecyclerSwipeAdapter<Post_adapter.MyViewHolder> {
    SearchView sv;
    private RecyclerView rv;
    private ArrayList<Post> post;
    private ArrayList<Vacancy> events;
    ViewGroup viewGroup;
    View view;
    private SharedPreferences preferences;

    public Post_adapter(ArrayList<Post> post){this.post=post;}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_info,viewGroup,false);
        preferences = Objects.requireNonNull(viewGroup.getContext()).getSharedPreferences("myPrefs", MODE_PRIVATE);
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

        if(preferences.getBoolean("is_admin",false)){
            myViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, myViewHolder.swipeLayout.findViewById(R.id.bottom_wraper));
        }else {
            myViewHolder.swipeLayout.findViewById(R.id.bottom_wraper).setVisibility(View.GONE);
        }

        if(post.get(position).getVacancy_id()!=1){
            myViewHolder.status.setText("Не свободен");
        }else {
            myViewHolder.status.setText("Свободен");

        }
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
        myViewHolder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preferences.getBoolean("is_admin",false)) {
                    ShowPopUp(view,position);
                }else {
                    Toast.makeText(view.getContext(), "You don't have access", Toast.LENGTH_SHORT).show();
                }
            }
        });


        myViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Edit will be near future", Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(preferences.getBoolean("is_admin",false)) {
                    NetworkService.getInstance().
                        getJSONApi().
                        deletePost(post.get(position).getId()).
                        enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    mItemManger.removeShownLayouts(myViewHolder.swipeLayout);
                                    post.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, post.size());
                                    mItemManger.closeAllItems();
                                        Toast.makeText(v.getContext(), "Deleted " + myViewHolder.name_surname.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("Error",t.toString());
                            }
                        });
                }else {
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
        return R.id.post_info_swipe;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name_surname,post_skills,status,send,edit,delete;
        SwipeLayout swipeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout=itemView.findViewById(R.id.post_info_swipe);
            name_surname=itemView.findViewById(R.id.post_f_name_and_l_name);
            post_skills=itemView.findViewById(R.id.post_skills);
            send=itemView.findViewById(R.id.post_info_send);
            edit=itemView.findViewById(R.id.post_info_edit);
            delete=itemView.findViewById(R.id.post_info_delete);
            status=itemView.findViewById(R.id.post_info_status);
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
    public void put(int id, Post post) {
        new NetworkService().
                getJSONApi().
                putPost(post.getId(), post.getF_name(), post.getL_name(), post.getMail(), post.getTelephon_number(), post.getCv_file_name(),
                        id, 1, 0, 0, 0,null).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.e("Success", "Success");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("Error", "error");
                    }
                });
    }
}
