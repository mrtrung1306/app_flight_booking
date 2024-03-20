package com.example.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.Post;

import java.util.List;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.ViewHolder> {
    List<Post> list;
    public AdapterPost(List<Post> listPost){
        this.list = listPost;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView= inflater.inflate(R.layout.item_post,parent,false);
        AdapterPost.ViewHolder viewHolder = new AdapterPost.ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPost.ViewHolder holder, int position) {
        Post post = list.get(position);
        holder.txtName.setText(post.title);
        holder.txtPrice.setText(post.price);
        Context context = holder.ivDiaDiem.getContext();
        int imageID = context.getResources().getIdentifier(post.image,"drawable", context.getPackageName());
        if (imageID != 0){
            holder.ivDiaDiem.setImageResource(imageID);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDiaDiem;
        TextView txtPrice, txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDiaDiem = itemView.findViewById(R.id.ivDiaDiem);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
