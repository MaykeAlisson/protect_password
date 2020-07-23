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

public class HomeActivity extends AppCompatActivity implements CustomDialogNovoGrupo.CustomDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.activty_home_toolbar);
        setTitle("Home");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
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

    public void openDialog(View view){
        CustomDialogNovoGrupo customDialogNovoGrupo = new CustomDialogNovoGrupo();
        customDialogNovoGrupo.show(getSupportFragmentManager(), "Adicionar Grupo");
    }

    @Override
    public void applyText(String nomeGrupo) {
        criarGrupo(nomeGrupo);
    }

    private void criarGrupo(final String possivelNomeGrupo){
        if (possivelNomeGrupo.trim().isEmpty()) {
            Toast.makeText(HomeActivity.this, "Nome Invalido!", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(HomeActivity.this, "Cria o grupo!", Toast.LENGTH_LONG).show();
    }

    public void criarRegistro(View view){
        Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(intent);
    }



}