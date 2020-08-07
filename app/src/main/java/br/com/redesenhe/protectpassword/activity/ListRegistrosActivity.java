package br.com.redesenhe.protectpassword.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import br.com.redesenhe.protectpassword.R;

public class ListRegistrosActivity extends AppCompatActivity {

    private Long idGrupo;
    private String nomeGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_registros);

        this.nomeGrupo = getIntent().getExtras().getString("nomeGrupo");
        this.idGrupo = getIntent().getExtras().getLong("idGrupo");

        Toolbar toolbar = findViewById(R.id.activty_list_registro_toolbar);
        setTitle(nomeGrupo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NovoRegistroActivity.class);
                startActivity(intent);
            }
        });

        init();
    }

    private void init() {
        buscaRegistros();
        configuraListViewRegistros();
    }

    private void buscaRegistros() {

    }

    private void configuraListViewRegistros() {
    }


}