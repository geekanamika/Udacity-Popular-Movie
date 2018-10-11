package com.example.android.popularmoviemvvmproject.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviemvvmproject.R;
import com.example.android.popularmoviemvvmproject.data.models.Favourites;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.ui.detail.review.ReviewFragment;
import com.example.android.popularmoviemvvmproject.ui.detail.trailer.TrailerFragment;
import com.example.android.popularmoviemvvmproject.utils.Constant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.fav_button)
    ImageButton favButton;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Constant constant;
    private Movie movieModel;
    private boolean isFavourite;

    private BottomSheetBehavior mBottomSheetBehavior;
    private DetailViewModel detailViewModel;

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
                movieModel = data.getParcelable(getString(R.string.extra_key));
                if (movieModel != null) {
                    setValuesToViews();
                    setUpTabLayout();
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

    private void setValuesToViews() {

        DetailModelFactory factory = new DetailModelFactory(this.getApplication(), movieModel.getId());
        detailViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);

        Picasso.with(this)
                .load(movieModel.getBackdropPath())
                .placeholder(R.drawable.placeholder)
                .into(backDropImage);
        String baseURL = "http://image.tmdb.org/t/p/w185/";
        Picasso.with(this)
                .load(baseURL + movieModel.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(moviePoster);
        Log.d("myTag", movieModel.getReleaseDate());
        movieTitle.setText(movieModel.getTitle());
        genres.setText(getString(R.string.genre_label, getGenreString(movieModel)));
        releaseDate.setText(getString(R.string.release_date_label, movieModel.getReleaseDate()));
        overView.setText(movieModel.getOverview());
        ratingBar.setNumStars(movieModel.getVoteAverage().intValue());
        setUpFavButton();

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

    private void setUpTabLayout() {

        ConstraintLayout mBottomSheet = findViewById(R.id.btmSheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putInt("movieId", movieModel.getId());
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

    @OnClick(R.id.fav_button)
    void favouriteIconClick() {
        if (!isFavourite) {
            Favourites fav = new Favourites(movieModel.getVoteCount(), movieModel.getId(),
                    movieModel.getVoteAverage(), movieModel.getTitle(), movieModel.getPopularity(),
                    movieModel.getPosterPath(), movieModel.getBackdropPath(), movieModel.getOverview(),
                    movieModel.getReleaseDate()

            );
            detailViewModel.setFavouriteMovie(fav);
            Log.d("myTag", "set movie as Favourite "+ movieModel.getId());
        } else {
            detailViewModel.removeFavouriteMovie(movieModel.getId());
            Log.d("myTag", "remove movie from Favourite "+ movieModel.getId());
        }

    }

    void setUpFavButton() {
        detailViewModel.getFavouriteStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    Log.d("myTag", "integer value of count "+ integer);
                    isFavourite = integer != 0;
                }
                favButton.setImageResource(isFavourite?R.drawable.fav_pressed:R.drawable.fav_not_pressed);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }

    }
}
