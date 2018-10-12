package com.example.android.popularmoviemvvmproject.ui.detail.review;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviemvvmproject.R;
import com.example.android.popularmoviemvvmproject.data.models.Review;
import com.example.android.popularmoviemvvmproject.utils.Constant;

import java.util.List;

/**
 * Created by Anamika Tripathi on 10/10/18.
 */
public class ReviewFragment extends Fragment {

    private ReviewAdapter reviewAdapter;
    private int movieId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movieId = bundle.getInt(Constant.BUNDLE_MOVIE_ID);
        } else {
            Log.d("myTag", "bundle is null");
        }
        return inflater.inflate(R.layout.tab_sheet_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        setUpReviewData();
    }

    private void setUpReviewData() {
        ReviewViewModel reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        reviewViewModel.startFetchingData(movieId);
        reviewViewModel.getReviewResults().observeForever(new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                reviewAdapter.setList(reviews);
            }
        });
    }

    private void init(View view) {
        RecyclerView reviewRecyclerView = view.findViewById(R.id.rv_reviews);
        reviewAdapter = new ReviewAdapter(getContext());
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewRecyclerView.setAdapter(reviewAdapter);
    }
}
