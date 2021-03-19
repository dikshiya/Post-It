package com.example.postit.posts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postit.LoadMoreVH;
import com.example.postit.OnItemClickListener;
import com.example.postit.R;
import com.example.postit.databinding.PostListItemBinding;
import com.example.postit.models.Post;
import com.example.postit.onLoadMoreListener;
import com.example.postit.utils.Utils;

import java.util.ArrayList;

public class PostListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final ArrayList<Post> postArrayList;
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private onLoadMoreListener loadMoreListener;
    private boolean loading;
    private boolean moreDataAvailable;

    PostListAdapter(Context context, ArrayList<Post> postArrayList){
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LoadMoreVH.TYPE_LOAD_MORE) {
            return new LoadMoreVH(LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false));
        }
        return new VHPosts(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Post post = postArrayList.get(position);
        if (post != null) {
            if (getItemViewType(position) != LoadMoreVH.TYPE_LOAD_MORE) {
                VHPosts holder = (VHPosts) viewHolder;

                if (position == getItemCount() - 1 && moreDataAvailable && !loading && loadMoreListener != null) {
                    loadMoreListener.onLoadMore();
                }
                if (post.getUser().getPicture() != null) {
                    Utils.displayImage(context, post.getUser().getPicture(), holder.postListBinding.ivUserImage);
                }
                if (post.getUser().getFirstName() != null) {
                    holder.postListBinding.tvUserFirstName.setText(post.getUser().getFirstName());
                }

                if (post.getUser().getLastName() != null) {
                    holder.postListBinding.tvUserLastName.setText(post.getUser().getLastName());
                }

                if (post.getUser().getEmail() != null) {
                    holder.postListBinding.tvUserEmail.setText(post.getUser().getEmail());
                }
                if (post.getPostImage() != null) {
                    Utils.displayImage(context, post.getPostImage(), holder.postListBinding.ivPostPicture);
                }
                if (post.getPostTitle() != null) {
                    holder.postListBinding.tvPostTitle.setText(post.getPostTitle());
                }
                if (post.getPostLikes() != 0) {
                    holder.postListBinding.tvPostLikes.setText(String.valueOf(post.getPostLikes()));
                }
                if (post.getLink() != null) {
                    holder.postListBinding.tvPostLink.setText(post.getLink());
                }
                if (post.getPublishDate() != null) {
                    holder.postListBinding.tvPostDate.setText(post.getPublishDate());
                }
                holder.postListBinding.tvPostLink.setOnClickListener(v -> {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(post.getLink()));
                    if (context.getPackageManager().resolveActivity(i, 0) != null) {
                        context.startActivity(i);
                    }
                });
                holder.itemView.setOnClickListener(v -> {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(viewHolder.getAdapterPosition());
                    }
                });
            }

        }
    }

        public void setOnItemClickListener (OnItemClickListener onItemClickListener){
            this.onItemClickListener = onItemClickListener;
        }

        public void setLoadMoreListener(onLoadMoreListener loadMoreListener) {
            this.loadMoreListener = loadMoreListener;
        }

        public void setLoading(boolean loading) {
            loading = loading;
        }

        public void setMoreDataAvailable(boolean moreDataAvailable) {
            moreDataAvailable = moreDataAvailable;
        }


        @Override
        public int getItemCount () {
            return postArrayList.size();
        }


    public static class VHPosts extends RecyclerView.ViewHolder {
        private final PostListItemBinding postListBinding;
        public VHPosts(@NonNull View itemView) {
            super(itemView);
            postListBinding = PostListItemBinding.bind(itemView);
        }
    }
}
