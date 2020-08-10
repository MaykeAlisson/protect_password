package br.com.redesenhe.protectpassword.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.redesenhe.protectpassword.R;

public class NovoRegistroActivity extends AppCompatActivity implements CustomDialogConfiguracaoSenha.CustomDialogListener{

    // Campos
    private EditText inputNome;
    private EditText inputUsuario;
    private EditText inputUrl;
    private EditText inputSenha;
    private EditText inputConfirmaSenha;
    private EditText inputComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_registro);

        Toolbar toolbar = findViewById(R.id.activty_novo_registro_toolbar);
        setTitle("Novo Registro");
        setSupportActionBar(toolbar);

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
    }

    public void openDialog(View view){
        CustomDialogConfiguracaoSenha customDialogConfiguracaoSenha = new CustomDialogConfiguracaoSenha();
        customDialogConfiguracaoSenha.show(getSupportFragmentManager(), "Gerar Senha");
    }

    public void onClickCancelar(View view){
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

    private void validaCampos(){

    }

    public void cadastroNovoRegistro(View view){
        validaCampos();
    }
}