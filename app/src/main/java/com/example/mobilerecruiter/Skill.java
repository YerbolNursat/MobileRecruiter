package com.example.mobilerecruiter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Skill {
    @SerializedName("f_name")
    @Expose
    String f_name="";
    @SerializedName("l_name")
    @Expose
    String l_name="";
    @SerializedName("tag")
    @Expose
    String tag="";

    public Skill(String f_name, String l_name, String tag) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.tag = tag;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
