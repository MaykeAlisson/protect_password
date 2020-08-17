package br.com.redesenhe.protectpassword.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.redesenhe.protectpassword.R;
import br.com.redesenhe.protectpassword.model.Registro;
import br.com.redesenhe.protectpassword.repository.IRegistroRepository;
import br.com.redesenhe.protectpassword.repository.impl.RegistroRepository;

public class NovoRegistroActivity extends AppCompatActivity implements CustomDialogConfiguracaoSenha.CustomDialogListener {

    // Repository
    IRegistroRepository registroRepository;

    // Campos
    private EditText inputNome;
    private EditText inputUsuario;
    private EditText inputUrl;
    private EditText inputSenha;
    private EditText inputConfirmaSenha;
    private EditText inputComentario;

    // Put Extra
    private Long idGrupo;
    private Long idRegistro;

    private Long id;
    private String dataCriacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_registro);

        Toolbar toolbar = findViewById(R.id.activty_novo_registro_toolbar);
        setTitle("Novo Registro");
        setSupportActionBar(toolbar);

        registroRepository = new RegistroRepository(getApplicationContext());

        this.idGrupo = getIntent().getExtras().getLong("idGrupo");
        this.idRegistro = getIntent().getLongExtra("idRegistro", 0);

        init();
    }

    private void init() {
        // inicia campos
        inputNome = findViewById(R.id.activity_novo_registro_nome);
        inputUsuario = findViewById(R.id.activity_novo_registro_usuario);
        inputUrl = findViewById(R.id.activity_novo_registro_url);
        inputSenha = findViewById(R.id.activity_novo_registro_senha);
        inputConfirmaSenha = findViewById(R.id.activity_novo_registro_confirmaSenha);
        inputComentario = findViewById(R.id.activity_novo_registro_comentario);

        if (idRegistro != 0) buscaRegistro(idRegistro);
    }

    private void buscaRegistro(final Long idRegistro) {
        Registro registro = registroRepository.buscaPorId(idRegistro);
        inputNome.setText(registro.getNome() == null ? "" : registro.getNome());
        inputUsuario.setText(registro.getUsuario() == null ? "" : registro.getUsuario());
        inputUrl.setText(registro.getUrl() == null ? "" : registro.getUrl());
        inputSenha.setText(registro.getSenha() == null ? "" : registro.getSenha());
        inputConfirmaSenha.setText(registro.getSenha() == null ? "" : registro.getSenha());
        inputComentario.setText(registro.getComentario() == null ? "" : registro.getComentario());

        id = registro.getId();
        idGrupo = registro.getIdGrupo();
        dataCriacao = registro.getDataCriacao();

    }

    public void openDialog(View view) {
        CustomDialogConfiguracaoSenha customDialogConfiguracaoSenha = new CustomDialogConfiguracaoSenha();
        customDialogConfiguracaoSenha.show(getSupportFragmentManager(), "Gerar Senha");
    }

    public void onClickCancelar(View view) {
        finish();
    }

    @Override
    public void applyText(String senhaGerada) {
        recuperaSenhaGerada(senhaGerada);
    }

    private void recuperaSenhaGerada(String senhaGerada) {
        if (senhaGerada.trim().isEmpty()) {
            Toast.makeText(this, "Senha Invalida!", Toast.LENGTH_LONG).show();
            return;
        }

        inputSenha.setText(senhaGerada);
        inputConfirmaSenha.setText(senhaGerada);

    }

    private boolean validaCampos() {

        if (inputNome.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Nome obrigatorio!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (inputSenha.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Senha obrigatoria!", Toast.LENGTH_LONG).show();
            return false;
        }

        String senha = inputSenha.getText().toString();
        String confirmaSenha = inputConfirmaSenha.getText().toString();

        if (!senha.equals(confirmaSenha)) {
            inputSenha.setError("Senhas diferentes");
            inputConfirmaSenha.setError("Senhas diferentes");
            Toast.makeText(this, "Senhas diferentes!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    public void cadastroNovoRegistro(View view) {

        if (!validaCampos()) return;

        if (idRegistro != 0) {
            if(atualizaRegistro()){
                Toast.makeText(this, "Registro atualizado!", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            Toast.makeText(this, "Erro ao atualizar Registro!", Toast.LENGTH_LONG).show();
            return;
        }

        Registro registro = new Registro.Builder()
                .comNome(inputNome.getText().toString())
                .comUsuario(inputUsuario.getText().toString())
                .comUrl(inputUrl.getText().toString())
                .comSenha(inputSenha.getText().toString())
                .comComentario(inputComentario.getText().toString())
                .comIdGrupo(idGrupo)
                .comDataCriacao(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                .build();

        if (registroRepository.salvar(registro)) {
            finish();
            return;
        }

        Toast.makeText(this, "Erro ao Salvar Registro!", Toast.LENGTH_LONG).show();
    }

    private boolean atualizaRegistro() {

        Registro registro = new Registro.Builder()
                .comId(id)
                .comNome(inputNome.getText().toString())
                .comUsuario(inputUsuario.getText().toString())
                .comUrl(inputUrl.getText().toString())
                .comSenha(inputSenha.getText().toString())
                .comComentario(inputComentario.getText().toString())
                .comIdGrupo(idGrupo)
                .comDataCriacao(dataCriacao)
                .build();

        return registroRepository.update(registro);

    }
}