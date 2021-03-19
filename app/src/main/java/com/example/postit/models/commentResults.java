package com.example.postit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class commentResults implements Serializable {
    @SerializedName("data")
    private ArrayList<Comment> commentList;

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }
}
