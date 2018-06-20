package com.example.acer.cinema;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CINEMA_CURSOR_LOADER_ID = 0;
    private CinemaCursorAdapter cinemaCursorAdapter;
    private RecyclerView recyclerViewCinema;


    private Button buttonVerFilmes;
    private Button buttonVerTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonVerFilmes = (Button) findViewById(R.id.buttonVerFilmes);
        buttonVerFilmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowVistos.class );
                startActivity(intent);
            }
        });


        buttonVerTodos = (Button) findViewById(R.id.buttonVerTodos);
        buttonVerTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowTodos.class );
                startActivity(intent);
            }
        });


                RecyclerView recyclerViewCinema = (RecyclerView) findViewById(R.id.recyclerViewCinema);

                        recyclerViewCinema.setLayoutManager(new LinearLayoutManager(this));
                cinemaCursorAdapter = new CinemaCursorAdapter(this);
                recyclerViewCinema.setAdapter(cinemaCursorAdapter);

                        getLoaderManager().initLoader(CINEMA_CURSOR_LOADER_ID, null,
                                (android.app.LoaderManager.LoaderCallbacks<Cursor>) this);


    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == CINEMA_CURSOR_LOADER_ID) {
                        return new CursorLoader(this, CinemaContentProvider.BOOKS_URI,
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