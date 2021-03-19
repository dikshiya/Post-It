package com.example.postit.models;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("publishDate")
    private String publishDate;

    @SerializedName("message")
    private String message;

    @SerializedName("owner")
    private userInfo user;

    @SerializedName("id")
    private String postId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

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

    public userInfo getUser() {
        return user;
    }

    public void setUser(userInfo user) {
        this.user = user;
    }
}
