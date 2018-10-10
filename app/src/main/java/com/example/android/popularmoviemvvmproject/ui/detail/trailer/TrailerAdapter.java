package com.example.android.popularmoviemvvmproject.ui.detail.trailer;

import android.content.Context;
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

    TrailerAdapter(Context context) {
        this.context = context;
        trailerList = new ArrayList<>();
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

    class TrailerHolder extends RecyclerView.ViewHolder {
        ImageView trailerImage;
        public TrailerHolder(@NonNull View itemView) {
            super(itemView);
            trailerImage = itemView.findViewById(R.id.img_trailer);
        }
    }
}
