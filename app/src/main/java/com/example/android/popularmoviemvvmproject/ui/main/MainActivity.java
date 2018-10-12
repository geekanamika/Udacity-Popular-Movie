package com.example.android.popularmoviemvvmproject.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.popularmoviemvvmproject.R;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.ui.detail.DetailActivity;
import com.example.android.popularmoviemvvmproject.utils.Constant;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MainActivityViewModel viewModel;
    private GridMovieAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar setup and setting title
        setUpToolbar();
        // initializing variables
        initGridView();

        // starting fetching data with popular filter
        viewModelSetUp();
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
        GridLayoutManager manager = new GridLayoutManager(this, 3,
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

        MenuItem item = menu.findItem(R.id.action_sort);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(MainActivity.this);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
        switch (i) {
            case 0:
                viewModel.setCurrentSortCriteria(Constant.POPULAR_SORT);
                viewModel.startFetchingData();
                break;
            case 1:
                viewModel.setCurrentSortCriteria(Constant.TOP_RATED_SORT);
                viewModel.startFetchingData();
                break;
            case 2:
                viewModel.setCurrentSortCriteria(Constant.FAVOURITE_SORT);
                viewModel.getFavouritesMovie().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        if(viewModel.getCurrentSortCriteria().equals(Constant.FAVOURITE_SORT)) {
                            adapter.setList(movies);
                        }
                    }
                });
                break;
            default:
                Log.e("myTag", "unknown item selected");
        }
        Log.d("myTag", "item selected " + i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
