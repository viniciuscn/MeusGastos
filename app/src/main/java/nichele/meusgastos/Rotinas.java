package nichele.meusgastos;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vinicius on 16/06/2016.
 */
public class Rotinas {

    public String UltimoDiaDoMes(int pano, int pmes){
        //String ano = String.format("%04d",new GregorianCalendar().get(Calendar.YEAR));
        String mes = String.format("%02d", pmes);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, pmes - 1);
        c.set(Calendar.YEAR, pano);

        String dia = String.format("%02d", c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return  pano + "-" + mes + "-" + dia;

    }

    public float format(String valor){
        return Float.parseFloat(valor.replaceAll(",","."));
    }

    public String format(String campo, String formato) {
        if (formato.equals("dd/mm/yyyy"))
            //campo = campo.substring(6,10)+"-"+campo.substring(3,5)+"-"+campo.substring(0,2);
            campo = campo.substring(8,10)+"/"+campo.substring(5,7)+"/"+campo.substring(0,4);
        return campo;
    }

    public String retornamesnumeral(String nomemes) {
        String mes="";
        switch (nomemes){
            case "Janeiro":
                mes="01";
                break;
            case "Fevereiro":
                mes="02";
                break;
            case "Março":
                mes="03";
                break;
            case "Abril":
                mes="04";
                break;
            case "Maio":
                mes="05";
                break;
            case "Junho":
                mes="06";
                break;
            case "Julho":
                mes="07";
                break;
            case "Agosto":
                mes="08";
                break;
            case "Setembro":
                mes="09";
                break;
            case "Outubro":
                mes="10";
                break;
            case "Novembro":
                mes="11";
                break;
            case "Dezembro":
                mes="12";
                break;
        }
        return mes;
    }

    public enum FormatoData{
        diamesano,
        anomesdia

    }

    public String montadata(FormatoData formato, String pdata) {
        SimpleDateFormat df = null;
        switch (formato){
            case diamesano:
                df = new SimpleDateFormat("dd/MM/yyyy");
                break;
            case anomesdia:
                df = new SimpleDateFormat("yyyy/MM/dd");
                break;

        }
        Date data=null;
        try {
            data = df.parse(pdata);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(data);
    }

    public String montadata(String pdata) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date data=null;
        try {
            data = df.parse(pdata);
            Log.i("montadata", pdata);
            Log.i("montadata", df.format(data));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("montadata",e.getMessage());
        }
        return df.format(data);
    }

    public void msgbox(Context contexto, String s) {
        Toast.makeText(contexto,s, Toast.LENGTH_SHORT).show();
    }

    public String formatavalor(float valor){
        DecimalFormat df=new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return df.format(valor);
    }

public void definecores(){

}
}
