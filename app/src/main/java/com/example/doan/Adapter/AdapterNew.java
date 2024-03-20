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
import com.example.doan.model.News;

import java.util.List;

public class AdapterNew extends RecyclerView.Adapter<AdapterNew.ViewHolder> {
    List<News> list;
    public AdapterNew(List<News> listNew){
        this.list = listNew;
    }
    @NonNull
    @Override
    public AdapterNew.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View newView= inflater.inflate(R.layout.item_news,parent,false);
        AdapterNew.ViewHolder viewHolder = new AdapterNew.ViewHolder(newView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNew.ViewHolder holder, int position) {
        News news = list.get(position);
        holder.txtName.setText(news.title);
        Context context = holder.ivNews.getContext();
        int imageID = context.getResources().getIdentifier(news.image,"drawable", context.getPackageName());
        if (imageID != 0){
            holder.ivNews.setImageResource(imageID);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNews;
        TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNews = itemView.findViewById(R.id.ivNews);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
