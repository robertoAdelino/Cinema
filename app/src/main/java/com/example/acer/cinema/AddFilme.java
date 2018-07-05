package com.example.acer.cinema;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddFilme extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public EditText editTexNome;
    public EditText editTextPoints;
    public Spinner spinnerCategories;
    public Filmes filmes;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        filmes = new Filmes();
        editTexNome = (EditText) findViewById(R.id.editTextNome);
        editTextPoints = (EditText) findViewById(R.id.editTextPoints);
        spinnerCategories = (Spinner) findViewById(R.id.spinnerCategories);






    }


    public void cancel(View view) {
        finish();
    }

    public void save(View view) {




        filmes.setTitle(editTexNome.getText().toString());
        filmes.setPoints(Double.parseDouble(editTextPoints.getText().toString()));
        filmes.setIdCategory((int) spinnerCategories.getSelectedItemId());

        int recordsAffected = getContentResolver().update(
                Uri.withAppendedPath(CinemaContentProvider.FILMES_URI, Integer.toString(filmes.getId())),
                DbTableFilmes.getContentValues(filmes),
                null,
                null
        );

        if (recordsAffected > 0) {
            Toast.makeText(this, "Movie saved successfully", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Toast.makeText(this, "Could not save Movie", Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
