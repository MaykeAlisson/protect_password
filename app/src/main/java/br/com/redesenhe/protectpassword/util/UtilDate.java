package br.com.redesenhe.protectpassword.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UtilDate {

    public static String dataString(final Date dataBase){

        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy ", Locale.getDefault());
        String dataFormatada = dt1.format(dataBase);

        return  dataFormatada;

    }

    public static Date convertStringData(final String dataString)  {

        Date data = null;

        try {
            SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy ", Locale.getDefault());
            data = dt1.parse(dataString);
        }catch (ParseException e){
             e.printStackTrace();
        }

        return  data;

    }
}
