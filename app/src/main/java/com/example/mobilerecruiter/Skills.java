package com.example.mobilerecruiter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Skills {
    @SerializedName("tag")
    @Expose
    private String tag;

    public Skills(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
