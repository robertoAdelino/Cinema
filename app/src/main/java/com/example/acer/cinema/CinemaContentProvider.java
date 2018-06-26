package com.example.acer.cinema;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CinemaContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.cinema";


    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CINEMA_URI = Uri.withAppendedPath(BASE_URI, DbTableFilmes.TABLE_NAME);


    private static final int FILMES = 100;
    private static final int FILMES_ID = 101;
    private static final int CLASSIFICACAO = 200;
    private static final int CLASSIFICACAO_ID = 201;
    private static final String MULTIPLE_ITEMS = "vnd.android.cursor.dir";
    private static final String SINGLE_ITEM = "vnd.android.cursor.item";


    private static UriMatcher getCinemaUriMatcher() {
                UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

                        uriMatcher.addURI("AUTHORITY", "filmes", FILMES);
                uriMatcher.addURI("AUTHORITY", "filmes/#", FILMES_ID);

                        uriMatcher.addURI("AUTHORITY", "classificacao", CLASSIFICACAO);
                uriMatcher.addURI("AUTHORITY", "classificacao/#", CLASSIFICACAO_ID);

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
        public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder){
        SQLiteDatabase db = cinemaOpenHelper.getReadableDatabase();

                        String id = uri.getLastPathSegment();

                        UriMatcher matcher = getCinemaUriMatcher();

                        switch (matcher.match(uri)) {
                       case FILMES:
                                return new DbTableFilmes(db).query(projection, selection, selectionArgs, null, null, sortOrder);

                        case CLASSIFICACAO:
                                return new DbTableCategories(db).query(projection, selection, selectionArgs, null, null, sortOrder);

                        case FILMES_ID:
                                return new DbTableFilmes(db).query(projection, DbTableFilmes._ID + "=?", new String[] { id }, null, null, null);

                        case CLASSIFICACAO_ID:
                                return new DbTableCategories(db).query(projection, DbTableCategories._ID + "=?", new String[] { id }, null, null, null);

                        default:
                                throw new UnsupportedOperationException("Invalid URI: " + uri);
                        }



    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        UriMatcher matcher = getCinemaUriMatcher();

        switch (matcher.match(uri)) {
            case FILMES:
                return MULTIPLE_ITEMS + "/" + AUTHORITY + "/" + DbTableFilmes.TABLE_NAME;

            case CLASSIFICACAO:
                return MULTIPLE_ITEMS + "/" + AUTHORITY + "/" + DbTableCategories.TABLE_NAME;

            case FILMES_ID:
                return SINGLE_ITEM + "/" + AUTHORITY + "/" + DbTableFilmes.TABLE_NAME;

            case CLASSIFICACAO_ID:
                return SINGLE_ITEM + "/" + AUTHORITY + "/" + DbTableCategories.TABLE_NAME;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);

        }
    }
    @Nullable

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = cinemaOpenHelper.getWritableDatabase();

        UriMatcher matcher = getCinemaUriMatcher();
        long id = -1;


        switch (matcher.match(uri)) {
                        case FILMES:
                                id = new DbTableFilmes(db).insert(values);
                                break;
                        case CLASSIFICACAO:
                                id = new DbTableCategories(db).insert(values);
                                break;

                        default:
                                throw new UnsupportedOperationException("Invalid URI: " + uri);
                        }

                if (id > 0) {
                        notifyChanges(uri);
                        return Uri.withAppendedPath(uri, Long.toString(id));
                    } else {
                        throw new SQLException("Could not insert record");
                    }
           }

            private void notifyChanges(@NonNull Uri uri) {
                getContext().getContentResolver().notifyChange(uri, null);



    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
                SQLiteDatabase db = cinemaOpenHelper.getWritableDatabase();

                        UriMatcher matcher = getCinemaUriMatcher();

                        String id = uri.getLastPathSegment();

                        int rows = 0;

                        switch (matcher.match(uri)) {
                        case FILMES_ID:
                                rows = new DbTableFilmes(db).delete(DbTableFilmes._ID +"=?", new String [] { id });
                                break;

                        case CLASSIFICACAO_ID:
                                rows = new DbTableCategories(db).delete(DbTableCategories._ID +"=?", new String [] { id });
                                break;

                        default:
                                throw new UnsupportedOperationException("Invalid URI: " + uri);
                        }

                        if (rows > 0) notifyChanges(uri);

                        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = cinemaOpenHelper.getWritableDatabase();

        UriMatcher matcher = getCinemaUriMatcher();

        String id = uri.getLastPathSegment();

        int rows = 0;

        switch (matcher.match(uri)) {
            case FILMES_ID:
                rows = new DbTableFilmes(db).update(values, DbTableFilmes._ID + "=?", new String[]{id});
                break;
            case CLASSIFICACAO_ID:
                rows = new DbTableCategories(db).update(values, DbTableCategories._ID + "=?", new String[]{id});
                break;

            default:
                throw new UnsupportedOperationException("Invalid URI: " + uri);
        }

        if (rows > 0) notifyChanges(uri);

        return rows;

    }
}
