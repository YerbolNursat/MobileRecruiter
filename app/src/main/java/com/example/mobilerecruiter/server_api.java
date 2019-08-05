package com.example.mobilerecruiter;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface server_api {
    @GET("/vacancies")
    Call<List<Vacancy>> getVacancies();
    @GET("/posts")
    Call<List<Post>> getPosts();
    @GET("/messages")
    Call<List<Message>> getMessages();
    @GET("/vacancy/{id}")
    Call<List<Vacancy>> getVacancyById(@Path("id") int id);
    @GET("/skills")
    Call<List<Skills>> getSkills();

    @POST("/post")
    @FormUrlEncoded
    Call<Void> postPost(@Field("f_name") String f_name,
                        @Field("l_name") String l_name,
                        @Field("mail") String mail,
                        @Field("telephon_number") String telephon_number,
                        @Field("cv_file_name") String cv_file_name,
                        @Field("vacancy_id") int vacancy_id,
                        @Field("tags")ArrayList<String> tags);
    @POST("/resend/storage")
    Call<JsonObject>  resendstorage(@Body RequestBody body);



}
