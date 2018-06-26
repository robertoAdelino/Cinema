package com.example.acer.cinema;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

                        DbCinemaOpenHelper DbCinemaOpenHelper = new DbCinemaOpenHelper(appContext);

                       SQLiteDatabase db = DbCinemaOpenHelper.getReadableDatabase();
                assertTrue("Could not open/create cinema database", db.isOpen());
               db.close();
            }






    @Test
    public void filmesCRUDtest() {
        DbCinemaOpenHelper DbCinemaOpenHelper = new DbCinemaOpenHelper(getContext());

        SQLiteDatabase db = DbCinemaOpenHelper.getWritableDatabase();

        DbTableFilmes tableFilmes = new DbTableFilmes(db);

        Filmes filmes = new Filmes();
        filmes.setName("Avatar");
        filmes.setClassificacao("Bom");
        filmes.setDate("11-11-11");
 ;

        // Insert/create (C)RUD
        long id = insertFilmes(tableFilmes, filmes);

        // query/read C(R)UD
        filmes = ReadFirstFilme(tableFilmes, "Avatar", id,"Bom");

        // update CR(U)D
        filmes.setName("Avatar!");
        int rowsAffected = tableFilmes.update(
                DbTableFilmes.getContentValues(filmes),
                DbTableFilmes._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to update filme", 1, rowsAffected);

        // query/read C(R)UD
        filmes = ReadFirstFilme(tableFilmes, "Avatar!",id,"Bom");

        // delete CRU(D)
        rowsAffected = tableFilmes.delete(
                DbTableFilmes._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to delete Filme", 1, rowsAffected);

        Cursor cursor = tableFilmes.query(DbTableFilmes.ALL_COLUMNS, null, null, null,
                null, null);
        assertEquals("Filmes found after delete ???", 0, cursor.getCount());
    }










    public void classificacaoCRUDtest() {
        DbCinemaOpenHelper DbCinemaOpenHelper = new DbCinemaOpenHelper(getContext());

        SQLiteDatabase db = DbCinemaOpenHelper.getWritableDatabase();

        DbTableFilmes tableFilmes = new DbTableFilmes(db);

        Filmes filmes = new Filmes();
        filmes.setName("Avatar");

        long idFilmes = insertFilmes(tableFilmes, filmes);

        DbTableClassificacao tableClassificacao = new DbTableClassificacao(db);

        // Insert/create (C)RUD
        Categorias classificacao = new Categorias();

        classificacao.setType("Bom");
        classificacao.setId((int) idFilmes);

        long id = tableClassificacao.insert(
                DbTableClassificacao.getContentValues(classificacao)
        );
        assertNotEquals("Failed to insert classificacao", -1, id);

        // query/read C(R)UD
        classificacao = ReadFirstClassificacao(tableClassificacao, "Bom",id,1);

        // update CR(U)D
        classificacao.setType("Bom");
        classificacao.setId(3);

        int rowsAffected = tableClassificacao.update(
                DbTableClassificacao.getContentValues(classificacao),
                DbTableClassificacao._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to update classificacao", 1, rowsAffected);

        // query/read C(R)UD
        classificacao = ReadFirstClassificacao(tableClassificacao, "Bom",id,3);

        // delete CRU(D)
        rowsAffected = tableClassificacao.delete(
                DbTableClassificacao._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to delete classificacao", 1, rowsAffected);

        Cursor cursor = tableClassificacao.query(DbTableClassificacao.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("classificacao found after delete ???", 0, cursor.getCount());
    }






    private Categorias ReadFirstClassificacao(DbTableClassificacao tableClassificacao,
                                              String expectedType,
                                              long expectedFilmesId,
                                              long expectedId) {

        Cursor cursor = tableClassificacao.query(DbTableClassificacao.ALL_COLUMNS, null,
                null, null, null, null);
        assertEquals("Failed to read classificacao", 1, cursor.getCount());

        assertTrue("Failed to read the first classificacao", cursor.moveToNext());

        Categorias classificacao = DbTableClassificacao.getCurrentClassificacaoFromCursor(cursor);

        assertEquals("Incorrect classificacao name", expectedType, classificacao.getType());
        assertEquals("Incorrect classificacao id", expectedId, classificacao.getId(), 0.001);


        return classificacao;
    }







    private long insertFilmes(DbTableFilmes tableFilmes, Filmes filmes) {
        long id = tableFilmes.insert(
                DbTableFilmes.getContentValues(filmes)
        );

        assertNotEquals("Failed to insert a filme", -1, id);

        return id;
    }



    private Filmes ReadFirstFilme(DbTableFilmes tableFilmes, String expectedName, long expectedId,String
            expectedClassificacao) {
        Cursor cursor = tableFilmes.query(DbTableFilmes.ALL_COLUMNS, null,
                null, null, null, null);
        assertEquals("Failed to read Filmes", 1, cursor.getCount());

        assertTrue("Failed to read the first category", cursor.moveToNext());

        Filmes filmes = DbTableFilmes.getCurrentFilmesFromCursor(cursor);
        assertEquals("Incorrect category name", expectedName, filmes.getName());
        assertEquals("Incorrect category id", expectedId, filmes.getId());
        assertEquals("Incorrect classificacao", expectedClassificacao, filmes.getClassificacao());

        return filmes;
    }








        private Context getContext() {
              return InstrumentationRegistry.getTargetContext();
            }
}
