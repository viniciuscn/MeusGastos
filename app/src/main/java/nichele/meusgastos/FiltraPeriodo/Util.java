package nichele.meusgastos.FiltraPeriodo;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

   enum DateInterval{
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

   public static String setDataPorExtenso(Date data, String formato){
      SimpleDateFormat sdf = null;
      //if (data.getYear() == new Date().getYear())
      if (formato.toLowerCase().equals("mmmm, yyyy")){
         sdf= new SimpleDateFormat("MMMM, yyyy");
         //return upperCaseFirst(sdf.format(data));
      }

      else if (formato.toLowerCase().equals("ddd, dd mmm yyyy")) {
         sdf= new SimpleDateFormat("EEE, dd MMM 'de' yyyy");

      }
      return upperCaseFirst(sdf.format(data));

      //else
         //sdf= new SimpleDateFormat("MMM/yy");

   }

   public static String upperCaseFirst(String value) {

      // Convert String to char array.
      char[] array = value.toCharArray();
      // Modify first element in array.
      array[0] = Character.toUpperCase(array[0]);
      // Return string.
      return new String(array);
   }

}
