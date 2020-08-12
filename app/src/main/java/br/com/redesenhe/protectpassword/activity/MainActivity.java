package br.com.redesenhe.protectpassword.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

import br.com.redesenhe.protectpassword.R;
import br.com.redesenhe.protectpassword.model.Usuario;
import br.com.redesenhe.protectpassword.repository.IUsuarioRepository;
import br.com.redesenhe.protectpassword.repository.impl.UsuarioRepository;
import br.com.redesenhe.protectpassword.util.DataBaseUtils;

import static br.com.redesenhe.protectpassword.util.Constantes.LOG_PROTECT;


public class MainActivity extends AppCompatActivity {

    private final int PERMISSAO_REQUEST = 1;

    // Repository
    IUsuarioRepository usuarioRepository;

    private boolean existeUsuario;
    Usuario usuario;

    // Campos
    private EditText inputSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        // Repository
        usuarioRepository = new UsuarioRepository(getApplicationContext());

        inputSenha = findViewById(R.id.activity_main_textSenha);

        init();
    }

    private void init() {


        // verifica se banco de dados existe se sim busca a senha
        // se nao cria banco

        existeUsuario = usuarioRepository.existeUsuario();

        if (existeUsuario) {
            usuario = usuarioRepository.buscar();
        }
//        solicitaPermisao();

    }

    private void solicitaPermisao() {
        //USUARIA DAR A PERMISSAO PARA LER
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }

        //USUARIA DAR A PERMISSAO PARA ESCREVER
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_main_baseDados:
                realizaBackupBase();
                break;

            case R.id.menu_main_reset:
                Toast.makeText(MainActivity.this, "Menu Resetar", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void realizaBackupBase() {
        solicitaPermisao();
        try {
            DataBaseUtils.backupDataBase(this);
//            String backupDBPath = Constantes.BACKUP_FOLDER + "/" + Constantes.DATABASE_NAME;
//            File backupFile = new File(Environment.getExternalStorageDirectory(), backupDBPath);
////            Toast.makeText(MainActivity.this, "parece tudo certo ", Toast.LENGTH_LONG).show();
        }catch (IOException e){
            Log.d(LOG_PROTECT, e.getMessage());
            e.printStackTrace();
        }


    }

    public void entrar(View view) {

        // se o arquivo existe e foi criado o map compara a senha de acesso;
        // se o arquivo e novo salva a senha no map e grava no arquivo
        // se senha correta ou gravada no map libera proxima tela

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);

/*
        if (TextUtils.isEmpty(inputSenha.getText())) {
            inputSenha.setError("Senha obrigatoria!");
            return;
        }

        String senhaDigitada = inputSenha.getText().toString();

        if (!existeUsuario) {

            String modelo = Build.MODEL;
            Date data = new Date();

            usuario = new Usuario.Builder()
                    .comDevice(modelo)
                    .comSenha(senhaDigitada)
                    .comDataCriacao(data)
                    .build();

            if (usuarioRepository.salvar(usuario)) {
                Toast.makeText(MainActivity.this, "USUARIO SALVO", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(MainActivity.this, "ERRO AO SALVAR USUARIO", Toast.LENGTH_LONG).show();
            return;
        }

        String senha = usuario.getSenha();

        if (senha.equals(senhaDigitada)) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            return;
        }

        Toast.makeText(MainActivity.this, "SENHA INVALIDA PARA USER: " + usuario.getDevice() , Toast.LENGTH_LONG).show();

 */

    }
}