package br.com.redesenhe.protectpassword.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.redesenhe.protectpassword.R;
import br.com.redesenhe.protectpassword.model.Registro;
import br.com.redesenhe.protectpassword.repository.IRegistroRepository;
import br.com.redesenhe.protectpassword.repository.impl.GrupoRepository;
import br.com.redesenhe.protectpassword.repository.impl.RegistroRepository;

import static java.util.Objects.requireNonNull;

public class ListRegistrosActivity extends AppCompatActivity {

    // Repository
    IRegistroRepository registroRepository;

    private List<Registro> registroList = new ArrayList<>();

    // Componentes
    private RecyclerView listViewDados;

    private Long idGrupo;
    private String nomeGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_registros);

        this.nomeGrupo = requireNonNull(getIntent().getExtras()).getString("nomeGrupo");
        this.idGrupo = getIntent().getExtras().getLong("idGrupo");

        // Repository
        registroRepository = new RegistroRepository(getApplicationContext());

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
        registroList = registroRepository.buscaTodosPorIdGrupo(idGrupo);
    }

    private void configuraListViewRegistros() {
    }


}