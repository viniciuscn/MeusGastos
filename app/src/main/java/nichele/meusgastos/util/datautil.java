package nichele.meusgastos.util;



import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class datautil {

   public enum DateInterval{
      dia,
      mes,
      ano
   }
   public static Date DateAdd(DateInterval interval, int number, Date datevalue){
      Date dataretornada = datevalue;
      Calendar c = Calendar.getInstance();
      c.setTime(dataretornada);

      switch (interval){
         case dia:
            c.add(Calendar.DATE, number);
            break;

         case mes:
            c.add(Calendar.MONTH, number);
            break;

         case ano:
            c.add(Calendar.YEAR, number);
            break;
      }
      return c.getTime();
   }

   public static String formatadata(Date data, String formato){
      SimpleDateFormat sdf = null;
      if (formato.toLowerCase().equals("mmmm, yyyy")){
         sdf= new SimpleDateFormat("MMMM, yyyy");
         return upperCaseFirst(sdf.format(data));
      } else if (formato.toLowerCase().equals("ddd, dd mmm yyyy")) {
         sdf = new SimpleDateFormat("EEE, dd MMM 'de' yyyy");
         return upperCaseFirst(sdf.format(data));
      }else if (formato.toLowerCase().equals("dddd, dd")) {
            sdf= new SimpleDateFormat("EEEE, dd");
            return upperCaseFirst(sdf.format(data)).replace("-feira","");
      }else{
         if (formato.toLowerCase().equals("dd/mm/yyyy")) {
            sdf= new SimpleDateFormat("dd/MM/yyyy");

         }else if (formato.toLowerCase().equals("yyyy-mm-dd")) {
            sdf= new SimpleDateFormat("yyyy-MM-dd");
         }
         return sdf.format(data);
      }

   }

   public static String upperCaseFirst(String value) {

      // Convert String to char array.
      char[] array = value.toCharArray();
      // Modify first element in array.
      array[0] = Character.toUpperCase(array[0]);
      // Return string.
      return new String(array);
   }

   public static String formatadata(String campo, String formato) {
      if (formato.toLowerCase().equals("yyyy-mm-dd"))
         campo = campo.substring(6, 10) + "-" + campo.substring(3, 5) + "-" + campo.substring(0, 2);
      if (formato.toLowerCase().equals("dd/mm/yyyy"))
         campo = campo.substring(8, 10) + "/" + campo.substring(5, 7) + "/" + campo.substring(0, 4);
      return campo;
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
         rotinas.logcat(pdata);
         rotinas.logcat( df.format(data));
      } catch (ParseException e) {
         e.printStackTrace();
         rotinas.logcat(e.getMessage());
      }
      return df.format(data);
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

   public String UltimoDiaDoMes(int pano, int pmes){
      //String ano = String.format("%04d",new GregorianCalendar().get(Calendar.YEAR));
      String mes = String.format("%02d", pmes);

      Calendar c = Calendar.getInstance();
      c.set(Calendar.MONTH, pmes - 1);
      c.set(Calendar.YEAR, pano);

      String dia = String.format("%02d", c.getActualMaximum(Calendar.DAY_OF_MONTH));
      return  pano + "-" + mes + "-" + dia;

   }
}
