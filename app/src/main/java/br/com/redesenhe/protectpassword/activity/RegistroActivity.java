package br.com.redesenhe.protectpassword.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.redesenhe.protectpassword.R;
import br.com.redesenhe.protectpassword.model.Registro;
import br.com.redesenhe.protectpassword.repository.IRegistroRepository;
import br.com.redesenhe.protectpassword.repository.impl.RegistroRepository;

import static java.util.Objects.requireNonNull;

public class RegistroActivity extends AppCompatActivity {

    // Repository
    IRegistroRepository registroRepository;

    private Long idRegistro;
    private String nomeRegistro;

    // Campos
    private EditText textNome;
    private EditText textUsuario;
    private EditText textUrl;
    private EditText textSenha;
    private EditText textComentario;
    private EditText textCriacao;
    private Button btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Repository
        registroRepository = new RegistroRepository(getApplicationContext());

        this.nomeRegistro = requireNonNull(getIntent().getExtras()).getString("nomeRegistro");
        this.idRegistro = getIntent().getExtras().getLong("idRegistro");

        Toolbar toolbar = findViewById(R.id.activty_registro_toolbar);
        setTitle(nomeRegistro);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        init();
    }

    private void init() {
        textNome = findViewById(R.id.activity_registro_nome);
        textNome.setFocusable(false);
        textUsuario = findViewById(R.id.activity_registro_usuario);
        textUsuario.setFocusable(false);
        textUrl = findViewById(R.id.activity_registro_url);
        textUrl.setFocusable(false);
        textSenha = findViewById(R.id.activity_registro_senha);
        textSenha.setFocusable(false);
        textComentario = findViewById(R.id.activity_registro_comentario);
        textComentario.setFocusable(false);
        textCriacao = findViewById(R.id.activity_registro_criacao);
        textCriacao.setFocusable(false);
        btnEditar = findViewById(R.id.activity_registro_btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NovoRegistroActivity.class);
                intent.putExtra("idRegistro", idRegistro);
                startActivity(intent);
                finish();
            }
        });

        buscaRegistro();
    }

    private void buscaRegistro() {
        Registro registro = registroRepository.buscaPorId(idRegistro);
        if(registro == null){
            Toast.makeText(RegistroActivity.this, "Menu Fechar", Toast.LENGTH_LONG).show();
            return;
        }

        textNome.setText(registro.getNome() != null ? registro.getNome() : "");
        textUsuario.setText(registro.getUsuario() != null ? registro.getUsuario() : "");
        textUrl.setText(registro.getUrl() != null ? registro.getUrl() : "");
        textSenha.setText(registro.getSenha() != null ? registro.getSenha() : "");
        textComentario.setText(registro.getComentario() != null ? registro.getComentario() : "");
        textCriacao.setText(registro.getDataCriacao() != null ? registro.getDataCriacao() : "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_registro_fechar:
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.menu_registro_mostrar_senha:
                textSenha.setInputType(InputType.TYPE_CLASS_TEXT);
                break;

            case R.id.menu_registro_copiar_usuario:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Usuario", textUsuario.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(RegistroActivity.this, "Usuario copiado", Toast.LENGTH_LONG).show();
                break;

            case R.id.menu_registro_copiar_senha:
                ClipboardManager clipboard2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip2 = ClipData.newPlainText("Senha", textSenha.getText().toString());
                clipboard2.setPrimaryClip(clip2);
                Toast.makeText(RegistroActivity.this, "Senha copiada", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}