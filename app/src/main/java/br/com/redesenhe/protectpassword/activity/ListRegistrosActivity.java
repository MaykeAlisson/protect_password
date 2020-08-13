package br.com.redesenhe.protectpassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.redesenhe.protectpassword.R;
import br.com.redesenhe.protectpassword.adapter.RegistroListAdapter;
import br.com.redesenhe.protectpassword.model.Registro;
import br.com.redesenhe.protectpassword.repository.IRegistroRepository;
import br.com.redesenhe.protectpassword.repository.impl.RegistroRepository;
import br.com.redesenhe.protectpassword.util.RecyclerItemClickListener;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class ListRegistrosActivity extends AppCompatActivity {

    // Repository
    IRegistroRepository registroRepository;

    private RegistroListAdapter adapter;

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
                intent.putExtra("idGrupo", idGrupo);
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
        adapter = new RegistroListAdapter(registroList);

        // config recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listViewDados.setLayoutManager(layoutManager);
        listViewDados.setHasFixedSize(true);
        listViewDados.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        listViewDados.setAdapter(adapter);

        configuraOnClickListView();

    }

    private void recarregaListViewRegistros() {
        // config adapter
        adapter = new RegistroListAdapter(registroList);

        // config recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listViewDados.setLayoutManager(layoutManager);
        listViewDados.setHasFixedSize(true);
        listViewDados.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        listViewDados.setAdapter(adapter);
    }

    private void configuraOnClickListView() {
        // evento click
        listViewDados.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        listViewDados,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(ListRegistrosActivity.this, RegistroActivity.class);
                                intent.putExtra("idRegistro", registroList.get(position).getId());
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                final String nome = registroList.get(position).getNome();
                                final long idRegistro = registroList.get(position).getId();
                                exibeDialogDeletaRegistro(nome, idRegistro);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }

    private void exibeDialogDeletaRegistro(String nome, final long idRegistro) {

        View view = LayoutInflater.from(this).inflate(R.layout.insere_texto_alerta, null);
        final TextView mTextAlerta = view.findViewById(R.id.textMensagem);
        final String mensagem = format("Deseja mesmo apagar o registro %s  ?", nome);
        mTextAlerta.setText(mensagem);

        final MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(this)
                .setTitle(R.string.deletar_grupo)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setCustomView(view, 20, 0, 20, 0)
                .setNegative("Cancelar", null)
                .setPositive("Deletar", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        registroRepository.deleta(idRegistro);
                        buscaRegistros();
                        recarregaListViewRegistros();
                    }
                }).build();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscaRegistros();
        recarregaListViewRegistros();
    }


}