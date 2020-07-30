package br.com.redesenhe.protectpassword.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.redesenhe.protectpassword.helper.DbHelper;
import br.com.redesenhe.protectpassword.model.Usuario;

import static br.com.redesenhe.protectpassword.system.Constantes.LOG_PROTECT;

public class UsuarioRepository implements IUsuarioRepository{

    private SQLiteDatabase set;
    private SQLiteDatabase get;

    public UsuarioRepository(Context context) {
        DbHelper db = new DbHelper( context );
        set = db.getWritableDatabase();
        get = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Usuario usuario) {

        ContentValues cv = new ContentValues();
        cv.put(DbHelper.USUARIO_COLUMN_DEVICE, usuario.getDevice() );
        cv.put(DbHelper.USUARIO_COLUMN_SENHA, usuario.getSenha() );
        cv.put(DbHelper.USUARIO_COLUMN_CRIACAO, usuario.getDataCriacao().toString() );

        try {
            set.insert(DbHelper.TABELA_USUARIO, null, cv );
            Log.i(LOG_PROTECT, "Usuario salvo com sucesso!");
        }catch (Exception e){
            Log.e(LOG_PROTECT, "Erro ao salvar usuario " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public Usuario buscar(Usuario usuario) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa() );

        try {
            String[] args = {tarefa.getId().toString()};
            get.
            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args );
            Log.i(LOG_PROTECT, "Tarefa atualizada com sucesso!");
        }catch (Exception e){
            Log.e(LOG_PROTECT, "Erro ao atualizada tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Usuario usuario) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa() );

        try {
            String[] args = {tarefa.getId().toString()};
            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args );
            Log.i(LOG_PROTECT, "Tarefa atualizada com sucesso!");
        }catch (Exception e){
            Log.e(LOG_PROTECT, "Erro ao atualizada tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = { tarefa.getId().toString() };
            escreve.delete(DbHelper.TABELA_TAREFAS, "id=?", args );
            Log.i("INFO", "Tarefa removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){

            Tarefa tarefa = new Tarefa();

            Long id = c.getLong( c.getColumnIndex("id") );
            String nomeTarefa = c.getString( c.getColumnIndex("nome") );

            tarefa.setId( id );
            tarefa.setNomeTarefa( nomeTarefa );

            tarefas.add( tarefa );
            Log.i("tarefaDao", tarefa.getNomeTarefa() );
        }

        return tarefas;

    }

}
