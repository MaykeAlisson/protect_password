package br.com.redesenhe.protectpassword.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.redesenhe.protectpassword.R;
import br.com.redesenhe.protectpassword.adapter.GrupoListAdapter;
import br.com.redesenhe.protectpassword.model.Grupo;
import br.com.redesenhe.protectpassword.repository.IGrupoRepository;
import br.com.redesenhe.protectpassword.repository.IUsuarioRepository;
import br.com.redesenhe.protectpassword.repository.impl.GrupoRepository;
import br.com.redesenhe.protectpassword.repository.impl.UsuarioRepository;
import br.com.redesenhe.protectpassword.util.Constantes;
import br.com.redesenhe.protectpassword.util.LoadingDialog;
import br.com.redesenhe.protectpassword.util.RecyclerItemClickListener;

import static br.com.redesenhe.protectpassword.util.Constantes.SHARED_PREFERENCES;
import static java.lang.String.format;

public class HomeActivity extends AppCompatActivity implements CustomDialogNovoGrupo.CustomDialogListener {

    // Repository
    IGrupoRepository grupoRepository;
    IUsuarioRepository usuarioRepository;

    // Preferencias do Usuario
    private SharedPreferences preferences;

    private List<Grupo> grupoList = new ArrayList<>();

    final LoadingDialog loadingDialog = new LoadingDialog(HomeActivity.this);

    // Componentes
    private RecyclerView listViewDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.activty_home_toolbar);
        setTitle("Home");
        setSupportActionBar(toolbar);

        loadingDialog.startLoading();

        preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

        // Repository
        grupoRepository = new GrupoRepository(getApplicationContext());
        usuarioRepository = new UsuarioRepository(getApplicationContext());

        init();
    }

    private void init() {
        listViewDados = findViewById(R.id.activity_home_listView);
        bucaDados();
        configuraListViewGrupo();
        loadingDialog.dismissDialog();
    }

    private void configuraListViewGrupo() {
        // config adapter
        GrupoListAdapter adapter = new GrupoListAdapter(grupoList);

        // config recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listViewDados.setLayoutManager(layoutManager);
        listViewDados.setHasFixedSize(true);
        listViewDados.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        listViewDados.setAdapter(adapter);

        configuraOnClickListView();
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
                                final String nome = grupoList.get(position).getNome();
                                final long idGrupo = grupoList.get(position).getId();

                                Intent intent = new Intent(HomeActivity.this, ListRegistrosActivity.class);
                                intent.putExtra("nomeGrupo", nome);
                                intent.putExtra("idGrupo", idGrupo);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                final String nome = grupoList.get(position).getNome();
                                final long idGrupo = grupoList.get(position).getId();
                                exibeDialogDeletaGrupo(nome, idGrupo);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }

    private void recarregaListViewGrupo(){
        // config adapter
        GrupoListAdapter adapter = new GrupoListAdapter(grupoList);

        // config recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listViewDados.setLayoutManager(layoutManager);
        listViewDados.setHasFixedSize(true);
        listViewDados.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        listViewDados.setAdapter(adapter);
    }


    private void exibeDialogDeletaGrupo(String nome, final long idGrupo) {

        View view = LayoutInflater.from(this).inflate(R.layout.insere_texto_alerta, null);
        final TextView mTextAlerta = view.findViewById(R.id.textMensagem);
        final String mensagem = format("Ao apagar o grupo %s todos os registros do grupo sera apagado. Deseja Continuar ?", nome);
        mTextAlerta.setText(mensagem);

        final MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(this)
                .setTitle(R.string.deletar_grupo)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setCustomView(view, 20, 0, 20, 0)
                .setNegative("Cancelar", null)
                .setPositive("Deletar", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        grupoRepository.deleta(idGrupo);
                        bucaDados();
                        recarregaListViewGrupo();
                    }
                }).build();
        dialog.show();
    }

    private void bucaDados() {
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
            case R.id.menu_home_fechar:
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.menu_home_mudar_senha:
                openMudarSenhaMaster();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openMudarSenhaMaster() {

        View view = LayoutInflater.from(this).inflate(R.layout.layout_mudar_senha_master, null);
        final EditText inputSenha = view.findViewById(R.id.layout_mudar_senha_senha);
        final EditText inputConfimaSenha = view.findViewById(R.id.layout_mudar_senha_confirmaSenha);


        final MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(this)
                .setIcon(R.drawable.icone_key)
                .setTitle(null)
                .setCustomView(view, 20, 20, 20, 0)
                .setNegative("Cancelar", null)
                .setPositive("Salvar", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (inputSenha.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Senha invalida!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (!inputSenha.getText().toString().equals(inputConfimaSenha.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Senhas diferentes!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        long idUser = preferences.getLong(Constantes.SHARED_PREFERENCES_ID_USER, 0);

                        usuarioRepository.atualizar(idUser, inputSenha.getText().toString());
                        
                    }
                })
                .build();
        dialog.show();
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
                .comDataCriacao(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                .build();

        if (grupoRepository.salvar(grupo)) {
            bucaDados();
            recarregaListViewGrupo();
            return;
        }

        Toast.makeText(HomeActivity.this, "Erro ao criar grupo", Toast.LENGTH_LONG).show();
    }

}