package com.example.mobilerecruiter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface server_api {
    @GET("/vacancies")
    Call<List<Vacancy>> getVacancies();
    @GET("/posts")
    Call<List<Post>> getPosts();
    @GET("/vacancy/{id}")
    Call<List<Vacancy>> getVacancyById(@Path("id") int id);

}
