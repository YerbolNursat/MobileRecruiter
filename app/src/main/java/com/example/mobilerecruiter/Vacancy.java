package com.example.mobilerecruiter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vacancy {

    @SerializedName("id")
    @Expose
    int id=1;

    @SerializedName("title")
    @Expose
    String title=null;

    @SerializedName("description")
    @Expose
    String description=null;

    @SerializedName("experience")
    @Expose
    String experience=null;

    @SerializedName("users_id")
    @Expose
    int user_id=0;

    public Vacancy() {
    }

    public Vacancy(int id,String title, String description, String experience, int user_id) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.experience = experience;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
