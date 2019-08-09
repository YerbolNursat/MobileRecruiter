package com.example.mobilerecruiter;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface server_api {
    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/post/{id}")
    Call<List<Post>> getPostById(@Path("id") int id);

    @GET("/posts/{id}")
    Call<List<Post>> getPostsById(@Path("id") int id);

    @PUT("/post")
    @FormUrlEncoded
    Call<Void> putPost(@Field("id") int id,
                       @Field("f_name") String f_name,
                       @Field("l_name") String l_name,
                       @Field("mail") String mail,
                       @Field("telephon_number") String telephon_number,
                       @Field("cv_file_name") String cv_file_name,
                       @Field("vacancy_id") int vacancy_id,
                       @Field("passed_cv")int passed_cv,
                       @Field("passed_customer")int passed_customer,
                       @Field("passed_interview")int passed_interview,
                       @Field("is_deployed")int is_deployed,
                       @Field("interview_time")String interview_time);


    @POST("/post")
    @FormUrlEncoded
    Call<Void> postPost(@Field("f_name") String f_name,
                        @Field("l_name") String l_name,
                        @Field("mail") String mail,
                        @Field("telephon_number") String telephon_number,
                        @Field("cv_file_name") String cv_file_name,
                        @Field("vacancy_id") int vacancy_id,
                        @Field("tags")ArrayList<String> tags);

    @DELETE("/post/{id}")
    Call<Void> deletePost(@Path("id") int id);

    @GET("/vacancies")
    Call<List<Vacancy>> getVacancies();

    @GET("/vacancies/{id}")
    Call<List<Vacancy>> getVacanciesById(@Path("id") int id);

    @GET("/vacancy/{id}")
    Call<List<Vacancy>> getVacancyById(@Path("id") int id);

    @POST("/vacancy")
    @FormUrlEncoded
    Call<Void> postVacancy(@Field("title") String title,
                           @Field("description") String description,
                           @Field("experience") String experience,
                           @Field("users_id") int user_id);

    @DELETE("/vacancy/{id}")
    Call<Void> deleteVacancy(@Path("id") int id);

    @GET("/skills")
    Call<List<Skills>> getSkills();
    @GET("/messages")
    Call<List<Message>> getMessages();
    @POST("/resend/storage")
    Call<JsonObject>  resendstorage(@Body RequestBody body);
    @POST("/login/token")
    Call<JsonObject>  checkToken(@Header("Authorization") String token);
    @POST("/login")
    @FormUrlEncoded
    Call<JsonObject>  createToken(@Field("username") String username,
                                  @Field("password") String password);
    @POST("/comment")
    @FormUrlEncoded
    Call<Void> postComment(@Field("description") String description,
                           @Field("post_id") int post_id,
                           @Field("users_id") int user_id);

}
