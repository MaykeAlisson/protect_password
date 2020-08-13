package br.com.redesenhe.protectpassword.repository.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

import br.com.redesenhe.protectpassword.helper.DbHelper;
import br.com.redesenhe.protectpassword.model.Usuario;
import br.com.redesenhe.protectpassword.repository.IUsuarioRepository;
import br.com.redesenhe.protectpassword.util.UtilCrypto;

import static br.com.redesenhe.protectpassword.helper.DbHelper.USUARIO_COLUMN_CRIACAO;
import static br.com.redesenhe.protectpassword.helper.DbHelper.USUARIO_COLUMN_DEVICE;
import static br.com.redesenhe.protectpassword.helper.DbHelper.USUARIO_COLUMN_ID;
import static br.com.redesenhe.protectpassword.helper.DbHelper.USUARIO_COLUMN_SENHA;
import static br.com.redesenhe.protectpassword.util.Constantes.LOG_PROTECT;
import static br.com.redesenhe.protectpassword.util.UtilCrypto.descriptografar;
import static br.com.redesenhe.protectpassword.util.UtilCrypto.encriptar;
import static br.com.redesenhe.protectpassword.util.UtilDate.convertStringData;
import static java.lang.String.format;

public class UsuarioRepository implements IUsuarioRepository {

    private SQLiteDatabase set;
    private SQLiteDatabase get;

    public UsuarioRepository(Context context) {
        DbHelper db = new DbHelper(context);
        set = db.getWritableDatabase();
        get = db.getReadableDatabase();
    }

    @Override
    public boolean existeUsuario() {

        String sql = format("SELECT * FROM %s ", DbHelper.TABELA_USUARIO);

        Cursor c = get.rawQuery(sql, null);
        int count = c.getCount();
        c.close();
        return count > 0;

    }

    @Override
    public boolean salvar(Usuario usuario) {

        ContentValues cv = new ContentValues();
        cv.put(USUARIO_COLUMN_DEVICE, usuario.getDevice());
        cv.put(USUARIO_COLUMN_SENHA, encriptar(usuario.getSenha()));
        cv.put(USUARIO_COLUMN_CRIACAO, usuario.getDataCriacao().toString());

        try {
            set.insert(DbHelper.TABELA_USUARIO, null, cv);
            Log.i(LOG_PROTECT, "Usuario salvo com sucesso!");
        } catch (Exception e) {
            Log.e(LOG_PROTECT, "Erro ao salvar usuario " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Usuario buscar() {

        String sql = format("SELECT * FROM %s LIMIT 1", DbHelper.TABELA_USUARIO);

        Cursor c = get.rawQuery(sql, null);

        Usuario usuario = null;

        while (c.moveToNext()) {

            Long id = c.getLong(c.getColumnIndex(USUARIO_COLUMN_ID));
            String device = c.getString(c.getColumnIndex(USUARIO_COLUMN_DEVICE));
            String senha = c.getString(c.getColumnIndex(USUARIO_COLUMN_SENHA));
            Date dataCriacao = convertStringData(c.getString(c.getColumnIndex(USUARIO_COLUMN_CRIACAO)));

            Usuario usuarioBuilder = new Usuario.Builder()
                    .comId(id)
                    .comDevice(device)
                    .comSenha(descriptografar(senha))
                    .comDataCriacao(dataCriacao)
                    .build();

            usuario = usuarioBuilder;

            Log.i(LOG_PROTECT, usuarioBuilder.getDevice());
        }

        c.close();

        return usuario;
    }

//    @Override
//    public boolean atualizar(Usuario usuario) {
//
//        ContentValues cv = new ContentValues();
//        cv.put("nome", tarefa.getNomeTarefa() );
//
//        try {
//            String[] args = {tarefa.getId().toString()};
//            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args );
//            Log.i(LOG_PROTECT, "Tarefa atualizada com sucesso!");
//        }catch (Exception e){
//            Log.e(LOG_PROTECT, "Erro ao atualizada tarefa " + e.getMessage() );
//            return false;
//        }
//
//        return true;
//    }

//    @Override
//    public boolean deletar(Tarefa tarefa) {
//
//        try {
//            String[] args = { tarefa.getId().toString() };
//            escreve.delete(DbHelper.TABELA_TAREFAS, "id=?", args );
//            Log.i("INFO", "Tarefa removida com sucesso!");
//        }catch (Exception e){
//            Log.e("INFO", "Erro ao remover tarefa " + e.getMessage() );
//            return false;
//        }
//
//        return true;
//    }

}
