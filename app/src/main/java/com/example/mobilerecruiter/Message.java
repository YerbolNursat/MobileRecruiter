package com.example.mobilerecruiter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("id")
    @Expose
    private String id;

    public Message(String date, String subject, String from, String filename, String id) {
        this.date = date;
        this.subject = subject;
        this.from = from;
        this.filename = filename;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
