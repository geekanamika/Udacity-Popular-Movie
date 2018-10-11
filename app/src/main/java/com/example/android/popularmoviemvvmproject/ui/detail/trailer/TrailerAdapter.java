package com.example.android.popularmoviemvvmproject.ui.detail.trailer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviemvvmproject.R;
import com.example.android.popularmoviemvvmproject.data.models.Review;
import com.example.android.popularmoviemvvmproject.data.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anamika Tripathi on 10/10/18.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    private Context context;
    private List<Trailer> trailerList;
    private final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    private final TrailerClickListener listener;

    TrailerAdapter(Context context, TrailerClickListener listener) {
        this.context = context;
        trailerList = new ArrayList<>();
        this.listener = listener;
    }

    public void setList(List<Trailer> reviews){
        trailerList = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trailer, viewGroup, false);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int i) {
        String baseURL = "http://img.youtube.com/vi/";
        Picasso.with(context)
                .load(baseURL + trailerList.get(i).getKey()+ "/0.jpg")
                .placeholder(R.drawable.placeholder)
                .into(holder.trailerImage);
    }

    @Override
    public int getItemCount() {
        if (trailerList.size()>0)
            return trailerList.size();
        else return 0;
    }

    class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView trailerImage;
        TrailerHolder(@NonNull View itemView) {
            super(itemView);
            trailerImage = itemView.findViewById(R.id.img_trailer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = getAdapterPosition();
            listener.onTrailerClick(YOUTUBE_BASE_URL + trailerList.get(id).getKey());
        }
    }

    interface TrailerClickListener {
        void onTrailerClick(String url);
    }
}
