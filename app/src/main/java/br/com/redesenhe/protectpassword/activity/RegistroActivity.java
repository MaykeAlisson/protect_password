package br.com.redesenhe.protectpassword.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

import br.com.redesenhe.protectpassword.R;

import static java.util.Objects.requireNonNull;

public class RegistroActivity extends AppCompatActivity {

    private Long idRegistro;
    private String nomeRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.nomeRegistro = requireNonNull(getIntent().getExtras()).getString("nomeRegistro");
        this.idRegistro = getIntent().getExtras().getLong("idRegistro");

        Toolbar toolbar = findViewById(R.id.activty_registro_toolbar);
        setTitle(nomeRegistro);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registro, menu);
        return super.onCreateOptionsMenu(menu);
    }
}