package com.example.postit.comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postit.R;
import com.example.postit.databinding.CommentListBinding;
import com.example.postit.models.Comment;
import com.example.postit.utils.Utils;

import java.util.ArrayList;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.VHComment>{
    private final ArrayList<Comment> commentArrayList;
    private final Context context;

    public CommentListAdapter(Context context, ArrayList<Comment> commentArrayList){
        this.context = context;
        this.commentArrayList = commentArrayList;
    }

    @NonNull
    @Override
    public VHComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list, parent, false);
        return new VHComment(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHComment holder, int position) {
        Comment comment = commentArrayList.get(position);
        if (comment!=null){
            if (comment.getUser().getPicture()!=null){
                Utils.displayImage(context, comment.getUser().getPicture(), holder.commentListBinding.ivUserImage);
            }
            if (comment.getUser().getFirstName()!=null){
                holder.commentListBinding.tvUserFirstName.setText(comment.getUser().getFirstName());
            }

            if (comment.getUser().getLastName()!=null){
                holder.commentListBinding.tvUserLastName.setText(comment.getUser().getLastName());
            }
            if (comment.getPublishDate()!=null){
                holder.commentListBinding.tvCommentDate.setText(comment.getPublishDate());
            }
            if (comment.getMessage()!=null){
                holder.commentListBinding.tvCommentMessage.setText(comment.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    public static class VHComment extends RecyclerView.ViewHolder{
        private final CommentListBinding commentListBinding;
        public VHComment(@NonNull View itemView) {
            super(itemView);
            commentListBinding = CommentListBinding.bind(itemView);
        }
    }
}
