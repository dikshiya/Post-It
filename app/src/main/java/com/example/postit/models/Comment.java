package com.example.postit.models;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("publishDate")
    private String publishDate;

    @SerializedName("message")
    private String message;

    @SerializedName("owner")
    private String user;

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
