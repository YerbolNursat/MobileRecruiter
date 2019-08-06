package com.example.mobilerecruiter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Post {
    @SerializedName("id")
    @Expose
    private int id=0;
    @SerializedName("f_name")
    @Expose
    private String f_name=null;
    @SerializedName("l_name")
    @Expose
    private String l_name=null;
    @SerializedName("mail")
    @Expose
    private String mail=null;
    @SerializedName("telephon_number")
    @Expose
    private String telephon_number=null;
    @SerializedName("description")
    @Expose
    private String description=null;
    @SerializedName("passed_cv")
    @Expose
    private int passed_cv=0;
    @SerializedName("passed_customer")
    @Expose
    private int passed_customer=0;
    @SerializedName("passed_interview")
    @Expose
    private int passed_interview=0;
    @SerializedName("is_deployed")
    @Expose
    private int is_deployed=0;
    @SerializedName("cv_file_name")
    @Expose
    private String cv_file_name=null;
    @SerializedName("username")
    @Expose
    private String username=null;
    @SerializedName("vacancy_id")
    @Expose
    private int vacancy_id=0;
    @SerializedName("comments")
    @Expose
    private ArrayList<Comment> comments;
    @SerializedName("skills")
    @Expose
    private ArrayList<String> skills;

    public Post() {

    }

    public Post(int id, String f_name, String l_name) {
        this.id = id;
        this.f_name = f_name;
        this.l_name = l_name;
    }

    public Post(String f_name, String l_name, String mail, String telephon_number, String cv_file_name, String username, int vacancy_id) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.mail = mail;
        this.telephon_number = telephon_number;
        this.cv_file_name = cv_file_name;
        this.username = username;
        this.vacancy_id = vacancy_id;
    }

    public Post(int id, String f_name, String l_name, String mail, String telephon_number, String description, int passed_cv, int passed_customer, int passed_interview, int is_deployed, String cv_file_name, String username, int vacancy_id, ArrayList<String> skills) {
        this.id = id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.mail = mail;
        this.telephon_number = telephon_number;
        this.description = description;
        this.passed_cv = passed_cv;
        this.passed_customer = passed_customer;
        this.passed_interview = passed_interview;
        this.is_deployed = is_deployed;
        this.cv_file_name = cv_file_name;
        this.username = username;
        this.vacancy_id = vacancy_id;
        this.skills = skills;
    }

    public Post(int id, String f_name, String l_name, String mail, String telephon_number, String description, int passed_cv, int passed_customer, int passed_interview, int is_deployed, String cv_file_name, String username, int vacancy_id, ArrayList<Comment> comments, ArrayList<String> skills) {
        this.id = id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.mail = mail;
        this.telephon_number = telephon_number;
        this.description = description;
        this.passed_cv = passed_cv;
        this.passed_customer = passed_customer;
        this.passed_interview = passed_interview;
        this.is_deployed = is_deployed;
        this.cv_file_name = cv_file_name;
        this.username = username;
        this.vacancy_id = vacancy_id;
        this.comments = comments;
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephon_number() {
        return telephon_number;
    }

    public void setTelephon_number(String telephon_number) {
        this.telephon_number = telephon_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPassed_cv() {
        return passed_cv;
    }

    public void setPassed_cv(int passed_cv) {
        this.passed_cv = passed_cv;
    }

    public int getPassed_customer() {
        return passed_customer;
    }

    public void setPassed_customer(int passed_customer) {
        this.passed_customer = passed_customer;
    }

    public int getPassed_interview() {
        return passed_interview;
    }

    public void setPassed_interview(int passed_interview) {
        this.passed_interview = passed_interview;
    }

    public int getIs_deployed() {
        return is_deployed;
    }

    public void setIs_deployed(int is_deployed) {
        this.is_deployed = is_deployed;
    }

    public int getVacancy_id() {
        return vacancy_id;
    }

    public void setVacancy_id(int vacancy_id) {
        this.vacancy_id = vacancy_id;
    }

    public String getCv_file_name() {
        return cv_file_name;
    }

    public void setCv_file_name(String cv_file_name) {
        this.cv_file_name = cv_file_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }
}