package com.example.android.popularmoviemvvmproject.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviemvvmproject.R;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anamika Tripathi on 28/8/18.
 */
public class GridMovieAdapter extends RecyclerView.Adapter<GridMovieAdapter.MyViewHolder> {
    private final Context context;
    private final ItemClickListener listener;
    private List<Movie> movieList;
    private String baseURL = "http://image.tmdb.org/t/p/w185/";

    public GridMovieAdapter(Context context, ItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        movieList = new ArrayList<>();
    }

    public void setList(List<Movie> movies){
        movieList = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.movieTitle.setText(movieList.get(position).getTitle());
        Picasso.with(context)
                .load(baseURL+ movieList.get(position).getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(holder.moviePoster);
        holder.movieReleaseYear.setText(movieList.get(position).getReleaseDate());
    }

    @Override
    public int getItemCount() {
        if (movieList.size()>0)
            return movieList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView moviePoster;
        private TextView movieTitle;
        private TextView movieReleaseYear;

        MyViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.item_movie_poster);
            movieTitle = itemView.findViewById(R.id.item_movie_title);
            movieReleaseYear = itemView.findViewById(R.id.item_release_year);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onItemClick(position);
        }
    }

    interface ItemClickListener {
        void onItemClick(int position);
    }
}
