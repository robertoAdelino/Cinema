package com.example.acer.cinema;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ShowVistos extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private CinemaCursorAdapter cinemaCursorAdapter;
    private RecyclerView recyclerViewCinema;
    private static final int CINEMA_CURSOR_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vistos);

        RecyclerView recyclerViewCinema = (RecyclerView) findViewById(R.id.recyclerViewCinema);

        recyclerViewCinema.setLayoutManager(new LinearLayoutManager(this));
        cinemaCursorAdapter = new CinemaCursorAdapter(this);
        recyclerViewCinema.setAdapter(cinemaCursorAdapter);

        getLoaderManager().initLoader(CINEMA_CURSOR_LOADER_ID, null,
                (android.app.LoaderManager.LoaderCallbacks<Cursor>) this);
    }
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == CINEMA_CURSOR_LOADER_ID) {
            return new CursorLoader(this, CinemaContentProvider.CINEMA_URI,
                    DbTableFilmes.ALL_COLUMNS, null, null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cinemaCursorAdapter.refreshData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cinemaCursorAdapter.refreshData(null);
    }
}
