package br.com.redesenhe.protectpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

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
}