package com.example.acer.cinema;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    private static final int CINEMA_CURSOR_LOADER_ID = 0;
    private Button buttonVerFilmes;
    private Button buttonVerTodos;
    private Spinner spinnerCategory;
    private Filmes filmes;
    private EditText editTextNomeFilme;
    private EditText editTextRealizador;
    private EditText editTextData;
    private Spinner spinnerClassificacao;

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

        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

      /*  int filmeId = intent.getIntExtra(MainActivity.Cin, -1);

        if (filmeId == -1) {
            finish();
            return;
        }

        Cursor cursorFilmes = getContentResolver().query(
                Uri.withAppendedPath(CinemaContentProvider.CINEMA_URI, Integer.toString(filmeId)),
                DbTableFilmes.ALL_COLUMNS,
                null,
                null,
                null
        );

        if (!cursorFilmes.moveToNext()) {
            Toast.makeText(this, "Filme not found", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
*/
        editTextNomeFilme = (EditText) findViewById(R.id.editTextNomeFilme);
        editTextRealizador = (EditText) findViewById(R.id.editTextRealizador);
        editTextData=(EditText) findViewById(R.id.editTextData);
      //  spinnerClassificacao = (Spinner) findViewById(R.id.knsjn );

      //  filmes = DbTableFilmes.getCurrentFilmesFromCursor(cursorFilmes);

       // editTextRealizador.setText(filmes.get());
        editTextNomeFilme.setText(filmes.getTitle());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getSupportLoaderManager().initLoader(CINEMA_CURSOR_LOADER_ID, null, this);





    }




}