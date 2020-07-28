package br.com.redesenhe.protectpassword.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static java.lang.String.format;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_PROTECT";

    public static String TABELA_USUARIO = "grupo";
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
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_USUARIO = format( "CREATE TABLE IF NOT EXISTS %s " +
                                     "( %s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                                     " %s TEXT NOT NULL, " +
                                     " %s TEXT ," +
                                     " %s TEXT NOT NULL); ",
                                     TABELA_USUARIO, USUARIO_COLUMN_ID, USUARIO_COLUMN_SENHA, USUARIO_COLUMN_DEVICE, USUARIO_COLUMN_CRIACAO);

        String SQL_USUARIO = "CREATE TABLE Periodo(codPeriodo INTEGER PRIMARY KEY AUTOINCREMENT, nomePeriodo TEXT)";

        String SQL_DISCIPLINA = "CREATE TABLE Disciplina(codDiscip INTEGER PRIMARY KEY AUTOINCREMENT, nomeDiscip TEXT, " +
                "periodoDiscip INTEGER, piDiscip INTEGER, FOREIGN KEY(periodoDiscip) REFERENCES Periodo(codPeriodo))";

        String SQL_USUARIO = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIO
                             + " ( INTEGER PRIMARY KEY AUTOINCREMENT, "
                             + " nome TEXT NOT NULL ); ";


        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABELA_TAREFAS + " ;";

        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao atualizar App");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao atualizar App" + e.getMessage());
        }

    }
}
