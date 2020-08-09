package br.com.redesenhe.protectpassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.redesenhe.protectpassword.R;
import br.com.redesenhe.protectpassword.adapter.RegistroListAdapter;
import br.com.redesenhe.protectpassword.model.Registro;
import br.com.redesenhe.protectpassword.repository.IRegistroRepository;
import br.com.redesenhe.protectpassword.repository.impl.RegistroRepository;
import br.com.redesenhe.protectpassword.util.RecyclerItemClickListener;

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
        listViewDados = findViewById(R.id.activity_list_registro_listView);
        buscaRegistros();
        configuraListViewRegistros();
    }

    private void buscaRegistros() {
        registroList = registroRepository.buscaTodosPorIdGrupo(idGrupo);
    }

    private void configuraListViewRegistros() {
        // config adapter
        RegistroListAdapter adapter = new RegistroListAdapter(registroList);

        // config recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listViewDados.setLayoutManager(layoutManager);
        listViewDados.setHasFixedSize(true);
        listViewDados.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        listViewDados.setAdapter(adapter);

        // evento click
        listViewDados.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        listViewDados,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                final String message = String.format("Registro %s selecionado!",
                                        registroList.get(position).getNome()
                                );

//                                Intent intent = new Intent(HomeActivity.this, ListRegistrosActivity.class);
//                                intent.putExtra("idConta", categoriasList.get(position).getDescricao());
//                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                final String message = String.format("Registro %s click long !",
                                        registroList.get(position).getNome()
                                );
                                final String nome = registroList.get(position).getNome();
                                final long idGrupo = registroList.get(position).getId();
//                                exibeDialogDeletaGrupo(nome, idGrupo);
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }


}