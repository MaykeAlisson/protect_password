package br.com.redesenhe.protectpassword.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import br.com.redesenhe.protectpassword.system.Constantes;

public class DataBaseUtils {

    private static final String SYSTEM_DB_FOLDER = "/data/br.com.redesenhe.protectpassword/databases";
    private static final String SYSTEM_DB_PATH = SYSTEM_DB_FOLDER + File.separator + Constantes.DATABASE_NAME;


    /**
     * @return
     * @throws IOException
     * <p><b>Autoria: </b>Mayke Alisson</p>
     */
    public static boolean backupDataBase(Context context) throws IOException  {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();

        if (sd.canWrite()) {

            String backupDBPath = Constantes.BACKUP_FOLDER + "/" + Constantes.DATABASE_NAME;
            File currentDB = new File(data, SYSTEM_DB_PATH);
            File backupDB = new File(sd, backupDBPath);

            if (currentDB.exists()) {
                
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());

                src.close();
                dst.close();

                Toast.makeText(context, "Backup feito com sucesso!", Toast.LENGTH_SHORT).show();

                return true;
            }
        }
        return false;
    }


}
