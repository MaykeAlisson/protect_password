package br.com.redesenhe.protectpassword;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

import br.com.redesenhe.protectpassword.model.Registro;
import br.com.redesenhe.protectpassword.system.UtilSystem;

import static br.com.redesenhe.protectpassword.system.Constantes.SYSTEM_FOLDER;


public class MainActivity extends AppCompatActivity {

    private final int PERMISSAO_REQUEST = 1;
    final UtilSystem utilSystem = new UtilSystem();

    final String path = Environment.getExternalStorageDirectory() + SYSTEM_FOLDER;


    Map<String, Object> mapDados = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.activty_main_toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {

        // verifica se banco de dados existe se sim busca a senha
        // se nao cria banco


//        solicitaPermisao();
        // teste
        Registro registro = new Registro.Builder()
                .comId("teste_id")
                .comNome("Registro")
                .comUsuario("Mayke")
                .comUrl("")
                .comSenha("123456")
                .comComentario("")
                .build();

//        mapDados.put(registro.getNome(), registro);

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
                Toast.makeText(MainActivity.this, "Menu Local Base", Toast.LENGTH_LONG).show();
                break;

            case R.id.menu_main_reset:
                Toast.makeText(MainActivity.this, "Menu Resetar", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void entrar(View view) {

        // se o arquivo existe e foi criado o map compara a senha de acesso;
        // se o arquivo e novo salva a senha no map e grava no arquivo
        // se senha correta ou gravada no map libera proxima tela

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}