package br.com.redesenhe.protectpassword.repository.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.redesenhe.protectpassword.helper.DbHelper;
import br.com.redesenhe.protectpassword.model.Grupo;
import br.com.redesenhe.protectpassword.model.Registro;
import br.com.redesenhe.protectpassword.repository.IRegistroRepository;

import static br.com.redesenhe.protectpassword.helper.DbHelper.GRUPO_COLUMN_CRIACAO;
import static br.com.redesenhe.protectpassword.helper.DbHelper.GRUPO_COLUMN_ID;
import static br.com.redesenhe.protectpassword.helper.DbHelper.GRUPO_COLUMN_NOME;
import static br.com.redesenhe.protectpassword.helper.DbHelper.REGISTRO_COLUMN_COMENTARIO;
import static br.com.redesenhe.protectpassword.helper.DbHelper.REGISTRO_COLUMN_CRIACAO;
import static br.com.redesenhe.protectpassword.helper.DbHelper.REGISTRO_COLUMN_ID_GRUPO;
import static br.com.redesenhe.protectpassword.helper.DbHelper.REGISTRO_COLUMN_NOME;
import static br.com.redesenhe.protectpassword.helper.DbHelper.REGISTRO_COLUMN_SENHA;
import static br.com.redesenhe.protectpassword.helper.DbHelper.REGISTRO_COLUMN_URL;
import static br.com.redesenhe.protectpassword.helper.DbHelper.REGISTRO_COLUMN_USUARIO;
import static br.com.redesenhe.protectpassword.helper.DbHelper.TABELA_GRUPO;
import static br.com.redesenhe.protectpassword.helper.DbHelper.TABELA_REGISTRO;
import static br.com.redesenhe.protectpassword.util.Constantes.LOG_PROTECT;

public class RegistroRepository implements IRegistroRepository {

    private SQLiteDatabase set;
    private SQLiteDatabase get;

    public RegistroRepository(Context context){
        DbHelper db = new DbHelper(context);
        set = db.getWritableDatabase();
        get = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Registro registro){
        ContentValues cv = new ContentValues();
        cv.put(REGISTRO_COLUMN_NOME, registro.getNome());
        cv.put(REGISTRO_COLUMN_USUARIO, registro.getUsuario());
        cv.put(REGISTRO_COLUMN_URL, registro.getUrl());
        cv.put(REGISTRO_COLUMN_SENHA, registro.getSenha());
        cv.put(REGISTRO_COLUMN_COMENTARIO, registro.getComentario());
        cv.put(REGISTRO_COLUMN_ID_GRUPO, registro.getIdGrupo());
        cv.put(REGISTRO_COLUMN_CRIACAO, registro.getDataCriacao().toString());

        try {
            set.insert(TABELA_REGISTRO, null, cv);
            Log.d(LOG_PROTECT, "Registro salvo com sucesso!");
        } catch (Exception e) {
            Log.e(LOG_PROTECT, "Erro ao salvar registro " + e.getMessage());
            return false;
        }

        return true;

    }

    @Override
    public List<Registro> buscaTodosPorIdGrupo(final Long idGrupo){

        List<Registro> listaRegistro = new ArrayList<>();

        String sql = String.format( "SELECT *" +
                                    " FROM %s" +
                                    " WHERE %s = %s;",
                                    TABELA_REGISTRO, REGISTRO_COLUMN_ID_GRUPO, idGrupo);

        Cursor c = get.rawQuery(sql, null);

        while ( c.moveToNext() ){

            Long id = c.getLong( c.getColumnIndex(GRUPO_COLUMN_ID) );
            String nome = c.getString( c.getColumnIndex(REGISTRO_COLUMN_NOME) );
            String usuario = c.getString( c.getColumnIndex(REGISTRO_COLUMN_USUARIO) );
            String url = c.getString( c.getColumnIndex(REGISTRO_COLUMN_URL) );
            String senha = c.getString( c.getColumnIndex(REGISTRO_COLUMN_SENHA) );
            String comentario = c.getString( c.getColumnIndex(REGISTRO_COLUMN_COMENTARIO) );
            Long grupo = c.getLong( c.getColumnIndex(REGISTRO_COLUMN_ID_GRUPO) );
//            String nome = c.getString( c.getColumnIndex(REGISTRO_COLUMN_CRIACAO) );

            Registro registro = new Registro.Builder()
                    .comId(id)
                    .comNome(nome)
                    .comUsuario(usuario)
                    .comUrl(url)
                    .comSenha(senha)
                    .comComentario(comentario)
                    .comIdGrupo(grupo)
                    .build();

            listaRegistro.add(registro);
        }

        Log.d(LOG_PROTECT, "REGISTRO_REPOSITORY - buscaTodos EXECUTADO" );

        c.close();

        return listaRegistro;
    }
}