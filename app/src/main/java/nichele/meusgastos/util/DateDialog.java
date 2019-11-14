package nichele.meusgastos.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.GregorianCalendar;

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    TextView lbldata;
    TextView txtdata;



    public DateDialog(View view, TextView data){
        lbldata =(TextView)view;
        txtdata =data;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        final Calendar c = Calendar.getInstance();

//        int year = gc.get(Calendar.YEAR);
//        int month = gc.get(Calendar.MONTH);
//        int day = gc.get(Calendar.DAY_OF_MONTH);

        int year = Integer.valueOf(txtdata.getText().subSequence(0,4).toString());
        int month = Integer.valueOf(txtdata.getText().subSequence(5,7).toString()) - 1;
        int day = Integer.valueOf(txtdata.getText().subSequence(8,10).toString());




        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //String date=String.format("%02d",day)+"/"+String.format("%02d",(month+1))+"/"+year;
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.YEAR, year);
        gc.set(GregorianCalendar.MONTH, month);
        gc.set(GregorianCalendar.DAY_OF_MONTH, day);
        lbldata.setText(datautil.formatadata(gc.getTime(), "ddd, dd mmm yyyy"));
        txtdata.setText(datautil.formatadata(gc.getTime(), "yyyy-mm-dd"));

    }
}
