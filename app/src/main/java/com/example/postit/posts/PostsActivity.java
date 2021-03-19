package com.example.postit.posts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postit.comments.CommentsActivity;
import com.example.postit.databinding.ActivityPostBinding;
import com.example.postit.models.Post;
import com.example.postit.models.postResults;
import com.example.postit.network.ApiClient;
import com.example.postit.network.ApiService;
import com.example.postit.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsActivity extends AppCompatActivity {
    public static final String POST_ID = "postID";
    private final ArrayList<Post> postsList = new ArrayList<>();
    private PostListAdapter postListAdapter;
    private ActivityPostBinding postBinding;
    private Post post;
    private int pageIndex = 0;
    private int itemLimit = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postBinding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(postBinding.getRoot());
        initAdapter();
        getPosts();
    }

    @NonNull
    private LinearLayoutManager getLayoutManager() {
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false );
        return manager;
    }

    private void initAdapter(){
        postBinding.rvPostList.setLayoutManager(getLayoutManager());
        postListAdapter = new PostListAdapter(this, postsList);
        postBinding.rvPostList.setAdapter(postListAdapter);
        postListAdapter.setOnItemClickListener(position -> {
            Intent i = new Intent(PostsActivity.this, CommentsActivity.class);
            post = postsList.get(position);
            i.putExtra(POST_ID, post);
            startActivity(i);
        });
        postListAdapter.setLoadMoreListener(() -> {
            pageIndex++;
            itemLimit+=10;
            getPosts();
        });
    }

    private void getPosts(){
        showProgress();
        ApiService allPosts = ApiClient.getInstance().createService(ApiService.class);
        allPosts.getPosts(pageIndex,itemLimit).enqueue(new Callback<postResults>() {
            @Override
            public void onResponse(Call<postResults> call, Response<postResults> response) {
                hideProgressBar();
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        if (response.body().getPostList()!=null){
                            if (pageIndex==0){
                            postsList.clear();}
                            else {hideLoadMore();}
                            postsList.addAll(response.body().getPostList());
                            postListAdapter.setMoreDataAvailable(!response.body().getPostList().isEmpty());
                            postListAdapter.setLoading(false);
                            postListAdapter.notifyDataSetChanged();
                        }
                    }else showEmptyScreen();
                }else Utils.showToast("Something went Wrong", PostsActivity.this);
            }

            @Override
            public void onFailure(Call<postResults> call, Throwable t) {
                if (pageIndex>0){
                    hideLoadMore();
                } else {
                    hideProgressBar();
                    showEmptyScreen();
                }
                Utils.showToast(t.getMessage() != null ? t.getMessage() : "Something went wrong. Please check you internet connection", PostsActivity.this);
            }
        });
    }
    private void showProgress() {
        if (pageIndex > 0) {
            showLoadMore();
        } else
            showProgressBar();
    }

    private void showLoadMore() {
        postBinding.rvPostList.post(() -> {
            Post post = new Post();
            post.setTypeLoad(true);
            postsList.add(post);
            postListAdapter.notifyItemInserted(postsList.size() - 1);
            postListAdapter.setLoading(true);
        });
    }

    private void hideLoadMore() {
        postsList.remove(postsList.size() - 1);
    }

    public void showProgressBar() {
        postBinding.progressCircular.setVisibility(View.VISIBLE);
        postBinding.rvPostList.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        postBinding.progressCircular.setVisibility(View.GONE);
        postBinding.rvPostList.setVisibility(View.VISIBLE);
    }

    private void showEmptyScreen() {
        postBinding.ivNoDataEmpty.setVisibility(View.VISIBLE);
        postBinding.tvEmptyView.setVisibility(View.VISIBLE);
    }
}