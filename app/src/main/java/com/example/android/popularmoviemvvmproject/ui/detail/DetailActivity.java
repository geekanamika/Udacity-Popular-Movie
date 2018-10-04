package com.example.android.popularmoviemvvmproject.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    private TextView genres, releaseDate, voteAverage, overView;
    private ImageView backDropImage, moviePosterBackGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // init views
        backDropImage = findViewById(R.id.header);
        genres = findViewById(R.id.genres);
        releaseDate = findViewById(R.id.release_date);
        voteAverage = findViewById(R.id.average_vote);
        overView = findViewById(R.id.overview);
        moviePosterBackGround = findViewById(R.id.movie_poster_background);

        // take data from extras
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

    private void initView(Movie model) {
        toolbarSetUp(model.getTitle());
        Picasso.with(this)
                .load(model.getBackdropPath())
                .placeholder(R.drawable.placeholder)
                .into(backDropImage);
        Picasso.with(this)
                .load(model.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(moviePosterBackGround);
        //Todo genre setup
        genres.setText(getGenreString(model));

        releaseDate.setText(getString(R.string.release_date_label, model.getReleaseDate()));
        voteAverage.setText(String.format(Locale.ENGLISH, "%3.1f", model.getVoteAverage()));
        overView.setText(model.getOverview());
    }

    private String getGenreString(Movie model) {
        StringBuilder s2 = new StringBuilder("");
        Constant constant = new Constant();
        if (model.getGenreIds().size() > 0) {
            for (int j = 0; j < model.getGenreIds().size(); j++) {
                int key = model.getGenreIds().get(j);
                s2.append(constant.getGenreList(key) + ", ");
            }
        }
        return s2.toString();
    }

    private void toolbarSetUp(String text) {
        Toolbar toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(text);
        }
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

}
