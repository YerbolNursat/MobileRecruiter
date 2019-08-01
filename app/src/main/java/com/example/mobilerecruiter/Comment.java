package com.example.mobilerecruiter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("cv_file_name")
    @Expose
    private String description=null;
    @SerializedName("username")
    @Expose
    private String username=null;

    public Comment() {
    }

    public Comment(String description, String username) {
        this.description = description;
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
