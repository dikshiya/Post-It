package com.example.postit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class postResults implements Serializable {
    @SerializedName("data")
    private ArrayList<Post> postList;

    public ArrayList<Post> getPostList() {
        return postList;
    }

    public void setPostList(ArrayList<Post> postList) {
        this.postList = postList;
    }

}
