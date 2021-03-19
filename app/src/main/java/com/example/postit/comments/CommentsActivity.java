package com.example.postit.comments;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postit.databinding.ActivityCommentsBinding;
import com.example.postit.models.Comment;
import com.example.postit.models.Post;
import com.example.postit.models.commentResults;
import com.example.postit.network.ApiClient;
import com.example.postit.network.ApiService;
import com.example.postit.posts.PostsActivity;
import com.example.postit.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CommentsActivity extends AppCompatActivity {
    private final ArrayList<Comment> commentsList = new ArrayList<>();
    private CommentListAdapter commentListAdapter;
    private ActivityCommentsBinding commentsBinding;
    private Post postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentsBinding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(commentsBinding.getRoot());
        postId = (Post) getIntent().getSerializableExtra(PostsActivity.POST_ID);
        initAdapter();
        getComments();
    }

    private void initAdapter(){
        commentsBinding.rvCommentList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        commentListAdapter = new CommentListAdapter(this, commentsList);
        commentsBinding.rvCommentList.setAdapter(commentListAdapter);
    }

    private void getComments(){
        showProgressBar();
        ApiService allcomments = ApiClient.getInstance().createService(ApiService.class);
        allcomments.getComments(postId.getPostId()).enqueue(new Callback<commentResults>() {
            @Override
            public void onResponse(Call<commentResults> call, Response<commentResults> response) {
                if (response.isSuccessful()){
                    hideProgressBar();
                    if (response.body()!=null){
                        if (response.body().getCommentList()!=null){
                            commentsList.clear();
                            commentsList.addAll(response.body().getCommentList());
                            if (commentsList.isEmpty()){showEmptyScreen();}
                            commentListAdapter.notifyDataSetChanged();
                        }
                    }else
                        showEmptyScreen();
                }else Utils.showToast("Something went Wrong", CommentsActivity.this);
            }

            @Override
            public void onFailure(Call<commentResults> call, Throwable t) {
                hideProgressBar();
                showEmptyScreen();
                Utils.showToast(t.getMessage() != null ? t.getMessage() : "Something went wrong. Please check you internet connection", CommentsActivity.this);
            }
        });
    }
    public void showProgressBar() {
        commentsBinding.progressCircular.setVisibility(View.VISIBLE);
        commentsBinding.rvCommentList.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        commentsBinding.progressCircular.setVisibility(View.GONE);
        commentsBinding.rvCommentList.setVisibility(View.VISIBLE);
    }

    private void showEmptyScreen() {
        commentsBinding.ivNoDataEmpty.setVisibility(View.VISIBLE);
        commentsBinding.tvEmptyView.setVisibility(View.VISIBLE);
    }
}