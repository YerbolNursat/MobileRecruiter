package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vacancy_adapter extends RecyclerSwipeAdapter<Vacancy_adapter.MyViewHolder> {
    ArrayList<Vacancy> vacancy;


    public Vacancy_adapter(ArrayList<Vacancy> vacancy){this.vacancy=vacancy;}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vacancy_info,viewGroup,false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final Vacancy_adapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.title.setText(vacancy.get(position).getTitle());
        myViewHolder.description.setText(vacancy.get(position).getDescription());

        myViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        myViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, myViewHolder.swipeLayout.findViewById(R.id.bottom_wraper));
        myViewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Vacancy_page.class);
                intent.putExtra("id",String.valueOf(vacancy.get(position).getId()));
                view.getContext().startActivity(intent);
            }
        });
        if(vacancy.get(position).getCandidates().size()>0){
            myViewHolder.status.setText("Кандидаты есть");
        }else {
            myViewHolder.status.setText("Кандидатов нету");
        }
        myViewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Edit will be near future", Toast.LENGTH_SHORT).show();
            }
        });
        myViewHolder.sender.setText(vacancy.get(position).getUsername());

        myViewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                NetworkService.getInstance().
                        getJSONApi().
                        deleteVacancy(vacancy.get(position).getId()).
                        enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    mItemManger.removeShownLayouts(myViewHolder.swipeLayout);
                                    vacancy.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, vacancy.size());
                                    mItemManger.closeAllItems();

                                    Toast.makeText(v.getContext(), "Deleted " + myViewHolder.title.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("Error",t.toString());
                            }
                        });
            }
        });
        mItemManger.bindView(myViewHolder.itemView, position);

    }


    @Override
    public int getItemCount() {
        return vacancy.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.vacancy_info_swipe;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,description,sender,status,Delete,Edit;
        public SwipeLayout swipeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.vacancy_info_vacancy_title);
            description=itemView.findViewById(R.id.vacancy_info_vacancy_description);
            Delete = itemView.findViewById(R.id.Delete);
            Edit = itemView.findViewById(R.id.Edit);
            swipeLayout=itemView.findViewById(R.id.vacancy_info_swipe);
            sender=itemView.findViewById(R.id.vacancy_info_vacancy_sender);
            status=itemView.findViewById(R.id.vacancy_info_status);
        }
    }
}
