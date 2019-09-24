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

   public static String setMesPorExtenso(Date data){
      SimpleDateFormat sdf;
      //if (data.getYear() == new Date().getYear())
         sdf= new SimpleDateFormat("MMMM, yyyy");
      //else
         //sdf= new SimpleDateFormat("MMM/yy");
      return upperCaseFirst(sdf.format(data));
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
