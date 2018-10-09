package com.example.android.popularmoviemvvmproject.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviemvvmproject.R;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    TextView genres, releaseDate, overView, movieTitle;

    ImageView backDropImage;
    ImageView moviePoster;
    AppCompatRatingBar ratingBar;
    private Constant constant;
    private String baseURL = "http://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // init views
        initVariables();


        // take data from extras
        extractDataFromBundle();
    }

    private void extractDataFromBundle() {
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        if (intent != null) {
            Bundle data = intent.getExtras();
            if (data != null) {
                // set values on text and images
                Movie model = data.getParcelable(getString(R.string.extra_key));
                if (model != null) {
                    initView(model);
                }

            }
        }
    }

    private void initVariables() {
        backDropImage = findViewById(R.id.img_movie_backdrop);
        moviePoster = findViewById(R.id.img_movie_poster);
        movieTitle = findViewById(R.id.movie_title);
        genres = findViewById(R.id.genres);
        releaseDate = findViewById(R.id.release_date);
        ratingBar = findViewById(R.id.rating);
        overView = findViewById(R.id.overview);

        // init constants for genres
        constant = new Constant();
        constant.createGenreList();

    }

    private void initView(Movie model) {
//        Picasso.with(this)
//                .load(model.getBackdropPath())
//                .placeholder(R.drawable.placeholder)
//                .into(backDropImage);
        Picasso.with(this)
                .load(baseURL + model.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(moviePoster);
        Log.d("myTag", model.getReleaseDate());
        movieTitle.setText(model.getTitle());
        genres.setText(getString(R.string.genre_label, getGenreString(model)));
        releaseDate.setText(getString(R.string.release_date_label, model.getReleaseDate()));
        overView.setText(model.getOverview());
        ratingBar.setNumStars(model.getVoteAverage().intValue());
    }

    private String getGenreString(Movie model) {
        StringBuilder s2 = new StringBuilder("");
        if (model.getGenreIds().size() > 0) {
            for (int j = 0; j < model.getGenreIds().size(); j++) {
                int key = model.getGenreIds().get(j);
                s2.append(constant.getGenreList(key) + ", ");
            }
        }
        return s2.toString();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
