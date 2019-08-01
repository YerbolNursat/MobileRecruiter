package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class On_customer_adapter extends RecyclerView.Adapter<On_customer_adapter.MyViewHolder> {
    ArrayList<Post> On_customer;
    ArrayList<Vacancy> vacancies;


    public On_customer_adapter(ArrayList<Post> On_customer){this.On_customer=On_customer;}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.on_customer_info,viewGroup,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.name_surname.setText(On_customer.get(position).getF_name()+" "+On_customer.get(position).getL_name());
        if(On_customer.get(position).getPassed_customer()==1||On_customer.get(position).getPassed_customer()==2){
            myViewHolder.status.setText(On_customer.get(position).getPassed_customer());

        }else {
            myViewHolder.status.setText("Резюме не просмотрено");

        }
//        NetworkService.getInstance()
//                .getJSONApi()
//                .getSkillsById(On_customer.get(position).getId())
//                .enqueue(new Callback<List<Skill>>() {
//                    @Override
//                    public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
//                        assert response.body() != null;
//                        post_skills= new ArrayList<>(response.body());
//                        StringBuilder description= new StringBuilder();
//                        for(int i=0;i<post_skills.size();i++){
//                            description.append(post_skills.get(i).getTag()).append(" ");
//                        }
//                        myViewHolder.skills.setText(description.toString());
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Skill>> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });

//        NetworkService.getInstance()
//                .getJSONApi()
//                .getVacancyById(On_customer.get(position).getVacancy_id())
//                .enqueue(new Callback<List<Vacancy>>() {
//                    @Override
//                    public void onResponse(Call<List<Vacancy>> call, Response<List<Vacancy>> response) {
//                        assert response.body() != null;
//                        vacancies=new ArrayList<>(response.body());
//                        myViewHolder.from.setText(vacancies.get(0).getTitle());
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Vacancy>> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
    }
    @Override
    public int getItemCount() {
        return On_customer.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name_surname,skills,status,from;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.on_customer_status);
            name_surname=itemView.findViewById(R.id.on_customer_f_name_and_l_name);
            skills=itemView.findViewById(R.id.on_customer_skills);
            from=itemView.findViewById(R.id.on_customer_from);

        }
    }
}
