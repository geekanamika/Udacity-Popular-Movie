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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.popularmoviemvvmproject.R;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.ui.detail.DetailActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MainActivityViewModel viewModel;
    private GridMovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar setup and setting title
        setUpToolbar();
        // initializing variables
        init();

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.startFetchingData("popular");
        viewModel.getMovieResults().observeForever(new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter.setList(movies);
                Log.d("myTag", "mainactivity:onchanged called");
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("MovieTime");
    }

    private void init() {
        RecyclerView movieGridView = findViewById(R.id.movie_list);
        adapter = new GridMovieAdapter(this, new GridMovieAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startDetailActivity(position);
            }
        });
        movieGridView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false);
        movieGridView.setLayoutManager(manager);
        movieGridView.addItemDecoration(new SpacesItemDecoration(4));
    }

    private void startDetailActivity(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(getString(R.string.extra_key), viewModel.getMovieResults().getValue().get(position));
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0)
            viewModel.startFetchingData("popular");
        else if (i == 1)
            viewModel.startFetchingData("top_rated");
        else {
            Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
