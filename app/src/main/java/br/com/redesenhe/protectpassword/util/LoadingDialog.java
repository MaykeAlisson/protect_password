package br.com.redesenhe.protectpassword.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import br.com.redesenhe.protectpassword.R;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity myActivity) {
        activity = myActivity;
    }

    public void startLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog, null));
        builder.setCancelable(false); // finaliza ao clic fora

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    public void dismissDialog() {
        dialog.dismiss();
    }

}
