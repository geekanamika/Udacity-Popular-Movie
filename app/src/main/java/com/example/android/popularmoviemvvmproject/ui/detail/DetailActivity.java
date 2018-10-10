package com.example.android.popularmoviemvvmproject.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviemvvmproject.R;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.ui.detail.review.ReviewFragment;
import com.example.android.popularmoviemvvmproject.ui.detail.trailer.TrailerFragment;
import com.example.android.popularmoviemvvmproject.utils.Constant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.genres)
    TextView genres;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.overview)
    TextView overView;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.img_movie_backdrop)
    ImageView backDropImage;
    @BindView(R.id.img_movie_poster)
    ImageView moviePoster;
    @BindView(R.id.rating)
    AppCompatRatingBar ratingBar;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Constant constant;

    private String baseURL = "http://image.tmdb.org/t/p/w185/";
    private BottomSheetBehavior mBottomSheetBehavior;

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
                    setValuesToViews(model);
                    setUpTabLayout(model.getId());
                }

            }
        }
    }

    private void initVariables() {
        ButterKnife.bind(this);

        // init constants for genres
        constant = new Constant();
        constant.createGenreList();
        // tablayout, viewpager init
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    private void setValuesToViews(Movie model) {
        Picasso.with(this)
                .load(model.getBackdropPath())
                .placeholder(R.drawable.placeholder)
                .into(backDropImage);
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

    private void setUpTabLayout(int id) {

        ConstraintLayout mBottomSheet = findViewById(R.id.btmSheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager, id);
        tabLayout.setupWithViewPager(viewPager, true);


    }

    private void setupViewPager(ViewPager viewPager, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("movieId", id );
        TrailerFragment trailerFragment = new TrailerFragment();
        ReviewFragment reviewFragment = new ReviewFragment();
        trailerFragment.setArguments(bundle);
        reviewFragment.setArguments(bundle);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new TrailerFragment(), "Trailers");
        adapter.addFragment(new ReviewFragment(), "Reviews");
        viewPager.setAdapter(adapter);
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
