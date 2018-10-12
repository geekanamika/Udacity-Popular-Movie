package com.example.android.popularmoviemvvmproject.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmoviemvvmproject.R;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.ui.detail.DetailActivity;
import com.example.android.popularmoviemvvmproject.utils.Constant;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private GridMovieAdapter adapter;
    private ProgressBar progressBar;
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar setup and setting title
        setUpToolbar();
        // initializing variables
        initGridView();

        //init bottom sheet for filter
        initBottomSheet();

        // starting fetching data with popular filter
        viewModelSetUp();
    }

    private void initBottomSheet() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.btm_sheet_filter, null);
        mBottomSheetDialog.setContentView(sheetView);

        // add listeners
        TextView popularSort =  sheetView.findViewById(R.id.sort_popular);
        TextView topRatedSort =  sheetView.findViewById(R.id.sort_top_rated);
        TextView favouriteSort =  sheetView.findViewById(R.id.sort_favourite);
        popularSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setCurrentSortCriteria(Constant.POPULAR_SORT);
                viewModel.startFetchingData();
                mBottomSheetDialog.dismiss();
                Log.d("myTag", "popular sort selected");
            }
        });
        topRatedSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setCurrentSortCriteria(Constant.TOP_RATED_SORT);
                viewModel.startFetchingData();
                mBottomSheetDialog.dismiss();
                Log.d("myTag", "top sort selected");
            }
        });
        favouriteSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                viewModel.setCurrentSortCriteria(Constant.FAVOURITE_SORT);
                viewModel.getFavouritesMovie().observe(MainActivity.this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        if(viewModel.getCurrentSortCriteria().equals(Constant.FAVOURITE_SORT)) {
                            adapter.setList(movies);
                            Log.d("myTag", "favourite sort selected");
                        }
                    }
                });
            }
        });
    }

    private void viewModelSetUp() {
        MainViewModelFactory factory = new MainViewModelFactory(this.getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);

        viewModel.getMovieResults().observeForever(new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null) {
                    if (movies.size() > 0)
                        adapter.setList(movies);
                }
            }
        });
        viewModel.getLoadingStatus().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading != null) {
                    // if true then show progress bar else hide it
                    if (isLoading) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("MovieTime");
    }

    private void initGridView() {
        RecyclerView movieGridView = findViewById(R.id.movie_list);
        adapter = new GridMovieAdapter(this, new GridMovieAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Movie movieDetail) {
                startDetailActivity(movieDetail);
            }
        });
        movieGridView.setAdapter(adapter);
        int recyclerViewSpanCount = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT ? 3 : 4;
        GridLayoutManager manager = new GridLayoutManager(this, recyclerViewSpanCount,
                GridLayoutManager.VERTICAL, false);
        movieGridView.setLayoutManager(manager);
        movieGridView.addItemDecoration(new SpacesItemDecoration(4));
        // progress init
        progressBar = findViewById(R.id.progress_bar);
    }

    /**
     * starts detail activity with passing movie model
     *
     * @param movieDetail : value to be passed to detail activity
     */
    private void startDetailActivity(Movie movieDetail) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(getString(R.string.extra_key), movieDetail);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_sort){
            mBottomSheetDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
