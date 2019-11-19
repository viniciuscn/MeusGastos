package nichele.meusgastos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.backup.CriaBackup;
import nichele.meusgastos.util.rotinas;
import petrov.kristiyan.colorpicker.ColorPicker;

public class actAjustesBD extends AppCompatActivity {


   Toolbar toolbar;
   Context context;

   Button btnexportaBD;

   Switch chkbackup;
   Button btnhorabackup;

   Button btncancelar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_ajustes_bd);
      overridePendingTransition(R.anim.filho_entrando,R.anim.main_saindo);
      carregatela();
   }

   private void carregatela(){
      toolbar = findViewById(R.id.toolbar);
      toolbar.setTitle("Cópia de Segurança");

      btnexportaBD = findViewById(R.id.btn_exportaBD);

      btnhorabackup = findViewById(R.id.btnhorabackup);
//      btnhorabackup.setText(sp.getString(rotinas.cfg_keyhorabkp, "23:00"));
//
      chkbackup = findViewById(R.id.chkbackup);
//				boolean rodar_bkp = sp.getBoolean(rotinas.cfg_keybkpativo, false);
//				chkbackup.setChecked(rodar_bkp);
//
//				if(rodar_bkp == true) {
//					btnhorabackup.setEnabled(true);
//				}else {
//					btnhorabackup.setEnabled(false);
//				}
//				chkbackup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//					@Override
//					public void onCheckedChanged(CompoundButton cb, boolean on){
//						if(on)
//							btnhorabackup.setEnabled(true);
//						else
//							btnhorabackup.setEnabled(false);
//					}
//				});
//
//				btnhorabackup.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						//final Calendar c = Calendar.getInstance();
//						//int mHour = c.get(Calendar.HOUR_OF_DAY);
//						//int mMinute = c.get(Calendar.MINUTE);
//
//						int mHour = new Integer(btnhorabackup.getText().subSequence(0, 2).toString());
//						int mMinute = new Integer(btnhorabackup.getText().subSequence(3,5).toString());
//
//
//						// Launch Time Picker Dialog
//						TimePickerDialog timePickerDialog = new TimePickerDialog(context,
//								new TimePickerDialog.OnTimeSetListener() {
//
//									@Override
//									public void onTimeSet(TimePicker view, int hourOfDay,
//									                      int minute) {
//
//										btnhorabackup.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d",minute));
//									}
//								}, mHour, mMinute, true);
//						timePickerDialog.show();
//
//					}
//				});
//
//				Button btnsalvar = dialoglayout.findViewById(R.id.btnsalvar);
//				btnsalvar.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//
//						SharedPreferences.Editor editor = sp.edit();
//						editor.putBoolean(rotinas.cfg_keybkpativo, chkbackup.isChecked());
//						editor.apply();
//
//						editor = sp.edit();
//						editor.putString(rotinas.cfg_keyhorabkp, chkbackup.isChecked() == true ? btnhorabackup.getText().toString() : "");
//						editor.apply();
//						rotinas.startAlertAtParticularTime(context);
//						ad.dismiss();
//					}
//				});
//
//				Button btncancelar = dialoglayout.findViewById(R.id.btncancelar);
//				btncancelar.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						ad.dismiss();
//					}
//				});
      carregatela2();
   }

   private void carregatela2(){
      context = this;
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);


      btnexportaBD.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
//
            LayoutInflater inflater = getLayoutInflater();
            final View dialoglayout = inflater.inflate(R.layout.activity_ajustes_copiaseguranca_exportabd, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setCancelable(false);

            alert.setView(dialoglayout);
            alert.setTitle("Exporta Banco de Dados");
            final AlertDialog ad = alert.show();

//						EditText txtnomearquivo = dialoglayout.findViewById(R.id.txtnomearquivo);
            final Switch chkenvemail = dialoglayout.findViewById(R.id.chkenvemail);
//
            Button btnsalvar = dialoglayout.findViewById(R.id.btnsalvar);
            btnsalvar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//								if(txtnomearquivo.getText().equals("") )
//									return;
                  new CriaBackup( context ).executeLocal( "", chkenvemail.isChecked() );
                  ad.dismiss();
               }
            });
         }
      });
   }


   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }
}
