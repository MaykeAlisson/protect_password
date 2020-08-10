package br.com.redesenhe.protectpassword.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import br.com.redesenhe.protectpassword.R;

public class CustomDialogConfiguracaoSenha extends AppCompatDialogFragment {

    // Componentes
    private EditText editTextSenhaGerada;
    private Button btnGerarSenha;
    private EditText editTextTamanhoSenha;
    private Button btnTamanhoSenha6;
    private Button btnTamanhoSenha8;
    private Button btnTamanhoSenha12;
    private CheckBox ckMaiuscula;
    private CheckBox ckMinuscula;
    private CheckBox ckDigito;
    private CheckBox ckUnderline;
    private CheckBox ckEspecial;

    // Listener
    private CustomDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_senha, null);
        builder.setView(view)
                .setTitle("Gerar Senha")
                .setPositiveButton("Aceitar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CustomDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Tem que implementar CustomDialogNovoGrupo");
        }

    }

    public interface CustomDialogListener{
        void applyText(String nomeGrupo);
    }

}
