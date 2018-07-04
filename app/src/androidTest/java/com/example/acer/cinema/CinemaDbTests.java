package com.example.acer.cinema;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CinemaDbTests {
    @Before
    public void setUp() {
        getContext().deleteDatabase(DbCinemaOpenHelper.DATABASE_NAME);
    }

    @Test
    public void openCinemaDbTest() {
        // Context of the app under test.
        Context appContext = getContext();

        DbCinemaOpenHelper dbCinemaOpenHelper = new DbCinemaOpenHelper(appContext);

        SQLiteDatabase db = dbCinemaOpenHelper.getReadableDatabase();
        assertTrue("Could not open/create Cinema database", db.isOpen());
        db.close();
    }

    @Test
    public void categoriesCRUDtest() {
        DbCinemaOpenHelper dbCinemaOpenHelper = new DbCinemaOpenHelper(getContext());

        SQLiteDatabase db = dbCinemaOpenHelper.getWritableDatabase();

        DbTableCategories tableCategories = new DbTableCategories(db);

        Categorias category = new Categorias();
        category.setName("SciFi");

        // Insert/create (C)RUD
        long id = insertCategorias(tableCategories, category);

        // query/read C(R)UD
        category = ReadFirstCategory(tableCategories, "SciFi", id);

        // update CR(U)D
        category.setName("Sci Fi");
        int rowsAffected = tableCategories.update(
                DbTableCategories.getContentValues(category),
                DbTableCategories._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to update category", 5, rowsAffected);

        // query/read C(R)UD
        category = ReadFirstCategory(tableCategories, "Sci-fi", id);

        // delete CRU(D)
        rowsAffected = tableCategories.delete(
                DbTableCategories._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to delete category", 1, rowsAffected);

        Cursor cursor = tableCategories.query(DbTableCategories.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Categories found after delete ???", 0, cursor.getCount());
    }

    @Test
    public void cinemasCRUDtest() {
        DbCinemaOpenHelper dbCinemaOpenHelper = new DbCinemaOpenHelper(getContext());

        SQLiteDatabase db = dbCinemaOpenHelper.getWritableDatabase();

        DbTableCategories tableCategories = new DbTableCategories(db);

        Categorias category = new Categorias();
        category.setName("Drama");

        long idCategory = insertCategorias(tableCategories, category);

        DbTableFilmes tableFilmes = new DbTableFilmes(db);

        // Insert/create (C)RUD
        Filmes filmes = new Filmes();

        filmes.setTitle("Interstelar");
        filmes.setPoints(15.0);
        filmes.setIdCategory((int) idCategory);

        long id = tableFilmes.insert(
                DbTableFilmes.getContentValues(filmes)
        );
        assertNotEquals("Failed to insert Movie", -1, id);

        // query/read C(R)UD
        filmes = ReadFirstFilme(tableFilmes, "Interstelar", 15.0, idCategory, id);
/////////
        ////////
        ///////
        /////////
        /////////
        //////////points


        // update CR(U)D
        filmes.setTitle("Interstelar");
        filmes.setPoints(18);

        int rowsAffected = tableFilmes.update(
                DbTableFilmes.getContentValues(filmes),
                DbTableFilmes._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to update Movie", 1, rowsAffected);

        // query/read C(R)UD
        filmes = ReadFirstFilme(tableFilmes, "Interstelar", 18, idCategory, id);

        // delete CRU(D)
        rowsAffected = tableFilmes.delete(
                DbTableFilmes._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to delete Movie", 1, rowsAffected);

        Cursor cursor = tableFilmes.query(DbTableFilmes.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Movies found after delete ???", 0, cursor.getCount());
    }

    private Filmes ReadFirstFilme(DbTableFilmes tableFilmes, String expectedTitle, double expectedPoints, long expectedCategoryId, long expectedId) {
        Cursor cursor = tableFilmes.query(DbTableFilmes.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Failed to read Movie", 4, cursor.getCount());

        assertTrue("Failed to read the first Movie", cursor.moveToNext());

        Filmes filmes = DbTableFilmes.getCurrentFilmeFromCursor(cursor);

        assertEquals("Incorrect Movie title", expectedTitle, filmes.getTitle());
        assertEquals("Incorrect Movie points", expectedPoints, filmes.getPoints(), 00.01);
        assertEquals("Incorrect Movie category", expectedCategoryId, filmes.getIdCategory());
        assertEquals("Incorrect Movie id", expectedId, filmes.getId());

        return filmes;
    }

    private long insertCategorias(DbTableCategories tableCategories, Categorias category) {
        long id = tableCategories.insert(
                DbTableCategories.getContentValues(category)
        );

        assertNotEquals("Failed to insert a category", -1, id);

        return id;
    }

    @NonNull
    private Categorias ReadFirstCategory(DbTableCategories tableCategories, String expectedName, long expectedId) {
        Cursor cursor = tableCategories.query(DbTableCategories.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Failed to read categories", 1, cursor.getCount());

        assertTrue("Failed to read the first category", cursor.moveToNext());

        Categorias category = DbTableCategories.getCurrentCategoryFromCursor(cursor);
        assertEquals("Incorrect category name", expectedName, category.getName());
        assertEquals("Incorrect category id", expectedId, category.getId());

        return category;
    }

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}
