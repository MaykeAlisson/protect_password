package br.com.redesenhe.protectpassword.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.FileUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.redesenhe.protectpassword.util.UtilSystem;

import static br.com.redesenhe.protectpassword.util.Constantes.DATABASE_NAME;
import static br.com.redesenhe.protectpassword.util.Constantes.DATA_BASE_VERSION;
import static br.com.redesenhe.protectpassword.util.Constantes.LOG_PROTECT;
import static java.lang.String.format;

public class DbHelper extends SQLiteOpenHelper {

    String SYSTEM_DB_FOLDER = "//data//br.com.redesenhe.protectpassword//databases//";

    public static String TABELA_USUARIO = "usuario";
    public static String USUARIO_COLUMN_ID = "id";
    public static String USUARIO_COLUMN_SENHA = "senha";
    public static String USUARIO_COLUMN_DEVICE = "dispositivo";
    public static String USUARIO_COLUMN_CRIACAO = "criacao";

    public static String TABELA_GRUPO = "grupo";
    public static String GRUPO_COLUMN_ID = "id";
    public static String GRUPO_COLUMN_NOME = "nome";
    public static String GRUPO_COLUMN_CRIACAO = "criacao";

    public static String TABELA_REGISTRO = "registro";
    public static String REGISTRO_COLUMN_ID = "id";
    public static String REGISTRO_COLUMN_NOME = "nome";
    public static String REGISTRO_COLUMN_USUARIO = "usuario";
    public static String REGISTRO_COLUMN_URL = "url";
    public static String REGISTRO_COLUMN_SENHA = "senha";
    public static String REGISTRO_COLUMN_COMENTARIO = "comentario";
    public static String REGISTRO_COLUMN_CRIACAO = "criacao";
    public static String REGISTRO_COLUMN_ID_GRUPO = "id_grupo";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_USUARIO = format( "CREATE TABLE IF NOT EXISTS %s " +
                                     "( %s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                                     " %s TEXT NOT NULL, " +
                                     " %s TEXT ," +
                                     " %s TEXT NOT NULL); ",
                                     TABELA_USUARIO, USUARIO_COLUMN_ID, USUARIO_COLUMN_SENHA, USUARIO_COLUMN_DEVICE, USUARIO_COLUMN_CRIACAO);

        String SQL_GRUPO = format(  "CREATE TABLE IF NOT EXISTS %s " +
                                    "( %s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                                    " %s TEXT NOT NULL," +
                                    " %s TEXT NOT NULL);",
                                    TABELA_GRUPO, GRUPO_COLUMN_ID, GRUPO_COLUMN_NOME, GRUPO_COLUMN_CRIACAO);

        String SQL_REGISTRO = format( "CREATE TABLE IF NOT EXISTS %s " +
                                      "( %s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                                      " %s TEXT NOT NULL, " +
                                      " %s TEXT , " +
                                      " %s TEXT , " +
                                      " %s TEXT NOT NULL, " +
                                      " %s TEXT , " +
                                      " %s INTEGER ," +
                                      " %s TEXT NOT NULL," +
                                      " FOREIGN KEY(%s) REFERENCES %s(%s)); ",
                                      TABELA_REGISTRO, REGISTRO_COLUMN_ID, REGISTRO_COLUMN_NOME,
                                      REGISTRO_COLUMN_USUARIO, REGISTRO_COLUMN_URL, REGISTRO_COLUMN_SENHA,
                                      REGISTRO_COLUMN_COMENTARIO, REGISTRO_COLUMN_ID_GRUPO, REGISTRO_COLUMN_CRIACAO,
                                      REGISTRO_COLUMN_ID_GRUPO, TABELA_GRUPO, GRUPO_COLUMN_ID);

        try {
            db.execSQL(SQL_USUARIO);
            db.execSQL(SQL_GRUPO);
            db.execSQL(SQL_REGISTRO);
            Log.d(LOG_PROTECT, "Sucesso ao criar a tabelas");
        } catch (Exception e) {
            Log.d(LOG_PROTECT, "Erro ao criar a tabelas" + e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        String sql = "DROP TABLE IF EXISTS " + TABELA_GRUPO;
        try {
//            db.execSQL(sql);
            onCreate(db);
            Log.d(LOG_PROTECT, "Sucesso ao atualizar App");
        } catch (Exception e) {
            Log.d(LOG_PROTECT, "Erro ao atualizar App" + e.getMessage());
        }

    }

    /**
     * Copies the database file at the specified location over the current
     * internal application database.
     * */
    public boolean importDatabase(String dbPath) throws IOException {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.
        close();
        File newDb = new File(dbPath);
        File oldDb = new File(SYSTEM_DB_FOLDER);
        if (newDb.exists()) {
            UtilSystem.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            getWritableDatabase().close();
            return true;
        }
        return false;
    }

}
