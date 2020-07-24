package br.com.redesenhe.protectpassword.system;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

import static br.com.redesenhe.protectpassword.system.Constantes.SYSTEM_FOLDER;

public class UtilSystem {

    final String path = Environment.getExternalStorageDirectory() + SYSTEM_FOLDER;
    final File pathDados = new File(path);
    File fileDadosGerada = new File(pathDados, "protectpassword.txt");

    public boolean arquivoExiste() {
        return pathDados.exists() && fileDadosGerada.exists();
    }

    public File criaArquivo() throws IOException {

        if (!pathDados.exists()) {
            boolean mkdirs = pathDados.mkdirs();
        }

        if (!fileDadosGerada.exists()) {
            boolean newFile = fileDadosGerada.createNewFile();
            if (newFile) return fileDadosGerada;
        }

        return null;
    }

    public void gravaNoArquivo(File arquivo){}

}
