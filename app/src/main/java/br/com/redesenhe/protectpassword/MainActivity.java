package br.com.redesenhe.protectpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.activty_main_toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {

        // Verifica se arquivo ja existe;
        // se existe abre, le e salva em um map
        // se nao existe cria o arquivo e cria
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_main_baseDados:
                Toast.makeText(MainActivity.this, "Menu Local Base", Toast.LENGTH_LONG).show();
                break;

            case R.id.menu_main_reset:
                Toast.makeText(MainActivity.this, "Menu Resetar", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void entrar(View view){

        // se o arquivo existe e foi criado o map compara a senha de acesso;
        // se o arquivo e novo salva a senha no map e grava no arquivo
        // se senha correta ou gravada no map libera proxima tela

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}