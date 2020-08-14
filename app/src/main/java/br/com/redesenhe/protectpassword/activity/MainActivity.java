package br.com.redesenhe.protectpassword.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import br.com.redesenhe.protectpassword.R;
import br.com.redesenhe.protectpassword.model.Usuario;
import br.com.redesenhe.protectpassword.repository.IUsuarioRepository;
import br.com.redesenhe.protectpassword.repository.impl.UsuarioRepository;
import br.com.redesenhe.protectpassword.util.Constantes;
import br.com.redesenhe.protectpassword.util.DataBaseUtils;

import static br.com.redesenhe.protectpassword.util.Constantes.LOG_PROTECT;
import static br.com.redesenhe.protectpassword.util.Constantes.SHARED_PREFERENCES;
import static br.com.redesenhe.protectpassword.util.Constantes.SHARED_PREFERENCES_ID_USER;
import static br.com.redesenhe.protectpassword.util.Constantes.VERSION;


public class MainActivity extends AppCompatActivity {

    private final int PERMISSAO_REQUEST = 1;

    // Preferencias do Usuario
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    // Repository
    IUsuarioRepository usuarioRepository;

    private boolean existeUsuario;
    Usuario usuario;

    // Campos
    private EditText inputSenha;
    private CheckBox mostrarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        editor = preferences.edit();

        // Repository
        usuarioRepository = new UsuarioRepository(getApplicationContext());

        inputSenha = findViewById(R.id.activity_main_textSenha);
        mostrarSenha = findViewById(R.id.activity_main_mostrarSenha);
        mostrarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mostrarSenha.isChecked()) {
                    inputSenha.setInputType(InputType.TYPE_CLASS_TEXT);
                    return;
                }
                inputSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        init();
    }

    private void init() {
        existeUsuario = usuarioRepository.existeUsuario();
        if (existeUsuario) {
            usuario = usuarioRepository.buscar();
        }
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
            case R.id.menu_main_sobre:
                openDialogSobre();
                break;
            case R.id.menu_main_baseDados:
                realizaBackupBase();
                break;

            case R.id.menu_main_import_base:
                importarBaseDeDados();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void importarBaseDeDados() {
    }

    private void openDialogSobre() {

        final String textoSobre = String.format("Protect Password e um gerenciador de senha. \n\n " +
                                                "Versão: %s \n " +
                                                "Desenvolvido por: Redesenhe ", VERSION);

        AlertDialog alerta;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sobre");
        builder.setMessage(textoSobre);
        alerta = builder.create();
        alerta.show();
    }

    private void realizaBackupBase() {
        solicitaPermisao();
        try {
            DataBaseUtils.backupDataBase(this);

            String backupDBPath = Constantes.BACKUP_FOLDER + Constantes.DATABASE_NAME;
            File backupFile = new File(Environment.getExternalStorageDirectory(), backupDBPath);

            Intent email = new Intent(android.content.Intent.ACTION_SEND);
            email.setType("application/octet-stream");
            email.putExtra(Intent.EXTRA_STREAM, Uri.parse(backupFile.getAbsolutePath()));
            email.putExtra(android.content.Intent.EXTRA_STREAM, Uri.parse("file:"+backupFile.getAbsolutePath()));
            startActivity(Intent.createChooser(email, "Enviar..."));

        } catch (IOException e) {
            Log.d(LOG_PROTECT, e.getMessage());
            e.printStackTrace();
        }
    }

    public void entrar(View view) {

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
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                editor.putLong(SHARED_PREFERENCES_ID_USER, usuarioRepository.buscar().getId());
                editor.commit();
                inputSenha.setText("");
                return;
            }

            Toast.makeText(MainActivity.this, "ERRO AO SALVAR USUARIO", Toast.LENGTH_LONG).show();
            return;
        }

        String senha = usuario.getSenha();
        editor.putLong(SHARED_PREFERENCES_ID_USER, usuario.getId());
        editor.commit();

        if (senha.equals(senhaDigitada)) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            inputSenha.setText("");
            return;
        }

        Toast.makeText(MainActivity.this, "SENHA INVALIDA PARA USER: " + usuario.getDevice(), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        existeUsuario = usuarioRepository.existeUsuario();
//        editor = preferences.edit();
    }
}