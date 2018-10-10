package com.example.android.popularmoviemvvmproject.ui.detail.trailer;

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
import com.example.android.popularmoviemvvmproject.data.models.Trailer;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Anamika Tripathi on 10/10/18.
 */
public class TrailerFragment extends Fragment {
    private TrailerViewModel trailerViewModel;
    private TrailerAdapter trailerAdapter;
    private int movieId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movieId = bundle.getInt("movieId");
        }
        return inflater.inflate(R.layout.tab_sheet_trailer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("myTag", "inside trailerFragment");

        init(view);

        setUpTrailerData();

    }

    private void setUpTrailerData() {
        trailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
        trailerViewModel.startFetchingData(movieId);
        trailerViewModel.getTrailerResults().observeForever(new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                trailerAdapter.setList(trailers);
                Log.d("myTag", "trailerFragment:onChanged called");
            }
        });
    }

    private void init(View view) {
        RecyclerView trailerRecyclerView = view.findViewById(R.id.rv_trailers);
        trailerAdapter = new TrailerAdapter(getContext());
        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trailerRecyclerView.setAdapter(trailerAdapter);
        //trailerRecyclerView.addItemDecoration(new DividerItemDecoration());
    }
}
