package com.example.acer.cinema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbCinemaOpenHelper extends SQLiteOpenHelper {

    private static final boolean PRODUCTION = false;

    public static final String DATABASE_NAME = "cinema.db";
    private static final int DATABASE_VERSION = 1;


    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public DbCinemaOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        DbTableClassificacao dbTableClassificacao = new DbTableClassificacao(db);
        dbTableClassificacao.create();

        DbTableFilmes dbTableFilmes = new DbTableFilmes(db);
        dbTableFilmes.create();

        if (!PRODUCTION) {
            seed(db);
        }
    }

    private void seed(SQLiteDatabase db) {
        DbTableClassificacao dbTableClassificacao = new DbTableClassificacao(db);

        Classificacao classificacao = new Classificacao();
        classificacao.setType("Bom");
        int idClassifcacaoBom = (int) dbTableClassificacao.insert(DbTableClassificacao.getContentValues(classificacao));

        classificacao = new Classificacao();
        classificacao.setType("Muito Bom");
        int idClassificacaoMuitoBom = (int) dbTableClassificacao.insert(DbTableClassificacao.getContentValues(classificacao));

        classificacao = new Classificacao();
        classificacao.setType("Mau");
        int idClassificacaoMau = (int) dbTableClassificacao.insert(DbTableClassificacao.getContentValues(classificacao));

        DbTableFilmes dbTableFilmes = new DbTableFilmes(db);

        Filmes filmes = new Filmes();
        filmes.setDate("11-05-2015");
        filmes.setId(idClassifcacaoBom);
        filmes.setName("Star Wars");
        dbTableFilmes.insert(DbTableFilmes.getContentValues(filmes));

        filmes = new Filmes();
        filmes.setDate("11-06-2016");
        filmes.setId(idClassificacaoMuitoBom);
        filmes.setName("Avatar");
        dbTableFilmes.insert(DbTableFilmes.getContentValues(filmes));

        filmes = new Filmes();
        filmes.setDate("11-07-2017");
        filmes.setId(idClassificacaoMau);
        filmes.setName("Liga da Justica");
        dbTableFilmes.insert(DbTableFilmes.getContentValues(filmes));
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
