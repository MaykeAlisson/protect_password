package br.com.redesenhe.protectpassword.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.redesenhe.protectpassword.helper.DbHelper;
import br.com.redesenhe.protectpassword.model.Grupo;

import static br.com.redesenhe.protectpassword.helper.DbHelper.GRUPO_COLUMN_CRIACAO;
import static br.com.redesenhe.protectpassword.helper.DbHelper.GRUPO_COLUMN_ID;
import static br.com.redesenhe.protectpassword.helper.DbHelper.GRUPO_COLUMN_NOME;
import static br.com.redesenhe.protectpassword.helper.DbHelper.TABELA_GRUPO;
import static br.com.redesenhe.protectpassword.system.Constantes.LOG_PROTECT;

public class GrupoRepository implements IGrupoRepository {

    private SQLiteDatabase set;
    private SQLiteDatabase get;

    public GrupoRepository(Context context){
        DbHelper db = new DbHelper(context);
        set = db.getWritableDatabase();
        get = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Grupo grupo){
        ContentValues cv = new ContentValues();
        cv.put(GRUPO_COLUMN_NOME, grupo.getNome());
        cv.put(GRUPO_COLUMN_CRIACAO, grupo.getDataCriacao().toString());

        try {
            set.insert(TABELA_GRUPO, null, cv);
            Log.i(LOG_PROTECT, "Grupo salvo com sucesso!");
        } catch (Exception e) {
            Log.e(LOG_PROTECT, "Erro ao salvar grupo " + e.getMessage());
            return false;
        }

        return true;

    }


    @Override
    public List<Grupo> buscaTodos(){

        List<Grupo> listaGrupo = new ArrayList<>();

        String sql = String.format("SELECT %s," +
                                    " %s" +
                                    " FROM %s",
                                    GRUPO_COLUMN_ID, GRUPO_COLUMN_NOME, TABELA_GRUPO);

        Cursor c = get.rawQuery(sql, null);

        while ( c.moveToNext() ){

            Long id = c.getLong( c.getColumnIndex(GRUPO_COLUMN_ID) );
            String nome = c.getString( c.getColumnIndex(GRUPO_COLUMN_NOME) );

            Grupo grupo = new Grupo.Builder()
                    .comId(id)
                    .comNome(nome)
                    .build();

            listaGrupo.add(grupo);
            Log.d(LOG_PROTECT, grupo.getNome() );
        }

        c.close();

        return listaGrupo;
    }

//    public Grupo buscaPorId(final Long id){
//
//    }
}
