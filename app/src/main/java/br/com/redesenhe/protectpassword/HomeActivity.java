package br.com.redesenhe.protectpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.redesenhe.protectpassword.adapter.GrupoListAdapter;
import br.com.redesenhe.protectpassword.model.Grupo;
import br.com.redesenhe.protectpassword.model.Registro;
import br.com.redesenhe.protectpassword.repository.GrupoRepository;
import br.com.redesenhe.protectpassword.repository.UsuarioRepository;
import br.com.redesenhe.protectpassword.util.RecyclerItemClickListener;

public class HomeActivity extends AppCompatActivity implements CustomDialogNovoGrupo.CustomDialogListener {

    // Repository
    GrupoRepository grupoRepository;

    private List<Grupo> grupoList = new ArrayList<>();
    private List<Registro> registroList = new ArrayList<>();

    // Componentes
    private RecyclerView listViewDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.activty_home_toolbar);
        setTitle("Home");
        setSupportActionBar(toolbar);

        // Repository
        grupoRepository = new GrupoRepository(getApplicationContext());

        init();
    }

    private void init() {
        listViewDados = findViewById(R.id.activity_home_listView);
        bucaDados();
        configuraGrupoAdapter();
    }

    private void configuraGrupoAdapter() {
        // config adapter
        GrupoListAdapter adapter = new GrupoListAdapter(grupoList);

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
                                final String message = String.format("Grupo %s selecionado!",
                                        grupoList.get(position).getNome()
                                );

//                                Intent intent = new Intent(ContasActivity.this, ContaDetalheActivity.class);
//                                intent.putExtra("idConta", categoriasList.get(position).getDescricao());
//                                startActivity(intent);

                                Toast.makeText(
                                        getApplicationContext(),
                                        message,
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                final String message = String.format("Grupo %s click long !",
                                        grupoList.get(position).getNome()
                                );

                                Toast.makeText(
                                        getApplicationContext(),
                                        message,
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }

    private void bucaDados() {

        // teste
//        Grupo grupo = new Grupo.Builder()
//                .comNome("Grupo 1")
//                .build();
//        grupoList.add(grupo);
//        Grupo grupo2 = new Grupo.Builder()
//                .comNome("Grupo 2")
//                .build();
//        grupoList.add(grupo2);
//        Grupo grupo3 = new Grupo.Builder()
//                .comNome("Grupo 3")
//                .build();
//        grupoList.add(grupo3);
//        Grupo grupo4 = new Grupo.Builder()
//                .comNome("Grupo 4")
//                .build();
//        grupoList.add(grupo4);
        // fim teste

        grupoList = grupoRepository.buscaTodos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_home_procurar:
                Toast.makeText(HomeActivity.this, "Menu Pesquisa", Toast.LENGTH_LONG).show();
                break;

            case R.id.menu_home_fechar:
                Toast.makeText(HomeActivity.this, "Menu Fechar", Toast.LENGTH_LONG).show();
                break;

            case R.id.menu_home_mudar_senha:
                Toast.makeText(HomeActivity.this, "Menu Mudar Senha", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDialog(View view) {
        CustomDialogNovoGrupo customDialogNovoGrupo = new CustomDialogNovoGrupo();
        customDialogNovoGrupo.show(getSupportFragmentManager(), "Adicionar Grupo");
    }

    @Override
    public void applyText(String nomeGrupo) {
        criarGrupo(nomeGrupo);
    }

    private void criarGrupo(final String possivelNomeGrupo) {
        if (possivelNomeGrupo.trim().isEmpty()) {
            Toast.makeText(HomeActivity.this, "Nome Invalido!", Toast.LENGTH_LONG).show();
            return;
        }

        Grupo grupo = new Grupo.Builder()
                .comNome(possivelNomeGrupo)
                .comDataCriacao(new Date())
                .build();

        if (grupoRepository.salvar(grupo)){
            bucaDados();
            configuraGrupoAdapter();
            return;
        }

        Toast.makeText(HomeActivity.this, "Erro ao criar grupo", Toast.LENGTH_LONG).show();
    }

    public void criarRegistro(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(intent);
    }


}