package com.example.acer.cinema;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonVerFilmes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonVerFilmes = (Button) findViewById(R.id.buttonVerFilmes);
        buttonVerFilmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verFilmesVistos();
            }
        });


    }

    public void verFilmesVistos(){
        Intent intent = new Intent(this, ShowVistosActivity.class);
        startActivity(intent);
    }


}