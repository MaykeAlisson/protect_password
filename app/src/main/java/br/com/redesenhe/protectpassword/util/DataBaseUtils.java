package br.com.redesenhe.protectpassword.util;

import android.content.Context;
import android.os.Environment;
import android.os.FileUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import br.com.redesenhe.protectpassword.helper.DbHelper;

import static br.com.redesenhe.protectpassword.util.Constantes.BACKUP_FOLDER;
import static br.com.redesenhe.protectpassword.util.Constantes.DATABASE_NAME;

public class DataBaseUtils {

    private static final String SYSTEM_DB_FOLDER = "//data//br.com.redesenhe.protectpassword//databases//";
    private static final String SYSTEM_DB_PATH = SYSTEM_DB_FOLDER + DATABASE_NAME;


    /**
     * @return
     * @throws IOException
     * <p><b>Autoria: </b>Mayke Alisson</p>
     */
    public static boolean backupDataBase(Context context) throws IOException  {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();

        if (sd.canWrite()) {

            File  backupFolder = new File(sd,BACKUP_FOLDER);
            String backupDBPath = BACKUP_FOLDER + DATABASE_NAME;
            File currentDB = new File(data, SYSTEM_DB_PATH);
            File backupDB = new File(sd, backupDBPath);

            if (currentDB.exists()) {

                if (!backupFolder.exists()){
                    backupFolder.mkdirs();
                }

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(context, "Backup feito com sucesso!", Toast.LENGTH_LONG).show();

                return true;
            }
        }
        return false;
    }

    public static boolean criaPastaImport(){

        File sd = Environment.getExternalStorageDirectory();

        if (sd.canWrite()) {

            File backupFolder = new File(sd, BACKUP_FOLDER);

            if (!backupFolder.exists()) {
               return backupFolder.mkdirs();
            }

            return true;
        }

        return false;
    }

    public static boolean importaDataBase(Context context) throws IOException {
        DbHelper db = new DbHelper(context);

        File sd = Environment.getExternalStorageDirectory();

        if (sd.canWrite()) {

            File backupFolder = new File(sd, BACKUP_FOLDER);

            if (!backupFolder.exists()){
                backupFolder.mkdirs();
            }

            return db.importDatabase(BACKUP_FOLDER);

        }

        return false;
    }

}
