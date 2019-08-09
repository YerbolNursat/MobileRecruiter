package com.example.mobilerecruiter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("candidates")
    @Expose
    ArrayList<Post> candidates;

    public Vacancy() {
    }
    public Vacancy(int id, String title, String description, String experience, String username, ArrayList<Post> candidates) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.experience = experience;
        this.username=username;
        this.candidates = candidates;
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


    public ArrayList<Post> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Post> candidates) {
        this.candidates = candidates;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
