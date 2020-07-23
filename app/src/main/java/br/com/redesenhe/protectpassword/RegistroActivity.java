package br.com.redesenhe.protectpassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Toolbar toolbar = findViewById(R.id.activty_registro_toolbar);
        setTitle("Novo Registro");
        setSupportActionBar(toolbar);
    }

    public void openDialog(View view){
        CustomDialogConfiguracaoSenha customDialogConfiguracaoSenha = new CustomDialogConfiguracaoSenha();
        customDialogConfiguracaoSenha.show(getSupportFragmentManager(), "Gerar Senha");
    }
}