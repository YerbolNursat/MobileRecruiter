package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Vacancy_adapter extends RecyclerView.Adapter<Vacancy_adapter.MyViewHolder> {
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
    public void onBindViewHolder(@NonNull Vacancy_adapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.title.setText(vacancy.get(position).getTitle());
        myViewHolder.description.setText(vacancy.get(position).getDescription());
        myViewHolder.experience.setText(vacancy.get(position).getExperience());
    }

    @Override
    public int getItemCount() {
        return vacancy.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,description,experience;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.vacancy_title);
            description=itemView.findViewById(R.id.vacancy_description);
            experience=itemView.findViewById(R.id.vacancy_experience);
        }
    }
}
