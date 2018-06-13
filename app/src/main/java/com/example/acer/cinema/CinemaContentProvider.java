package com.example.acer.cinema;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CinemaContentProvider extends ContentProvider {

    private static final int FILMES = 100;
    private static final int FILMES_ID = 101;
    private static final int CLASSIFICACAO = 200;
    private static final int CLASSIFICACAO_ID = 201;


    private static UriMatcher getCinemaUriMatcher() {
                UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

                        uriMatcher.addURI("com.example.acer.cinema", "filmes", FILMES);
                uriMatcher.addURI("com.example.acer.cinema", "filmes/#", FILMES_ID);

                        uriMatcher.addURI("com.example.acer.cinema", "classificacao", CLASSIFICACAO);
                uriMatcher.addURI("com.example.acer.cinema", "classificacao/#", CLASSIFICACAO_ID);

                        return uriMatcher;
            }



    DbCinemaOpenHelper cinemaOpenHelper;

    @Override
    public boolean onCreate() {
        cinemaOpenHelper = new DbCinemaOpenHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        SQLiteDatabase db = cinemaOpenHelper.getReadableDatabase();

                        String id = uri.getLastPathSegment();

                        UriMatcher matcher = getCinemaUriMatcher();

                        switch (matcher.match(uri)) {
                       case FILMES:
                                return new DbTableFilmes(db).query(projection, selection, selectionArgs, null, null, sortOrder);

                        case CLASSIFICACAO:
                                return new DbTableClassificacao(db).query(projection, selection, selectionArgs, null, null, sortOrder);

                        case FILMES_ID:
                                return new DbTableFilmes(db).query(projection, DbTableFilmes._ID + "=?", new String[] { id }, null, null, null);

                        case CLASSIFICACAO_ID:
                                return new DbTableClassificacao(db).query(projection, DbTableClassificacao._ID + "=?", new String[] { id }, null, null, null);

                        default:
                                throw new UnsupportedOperationException("Invalid URI: " + uri);
                        }



    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
