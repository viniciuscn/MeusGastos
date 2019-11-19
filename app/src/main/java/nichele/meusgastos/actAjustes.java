package nichele.meusgastos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import nichele.meusgastos.backup.CriaBackup;
import nichele.meusgastos.util.rotinas;

public class actAjustes extends AppCompatActivity {

	Context context;
	private Toolbar mToolbar;

	SharedPreferences sp;

	LinearLayout laycomecardozero;
	LinearLayout laycopiaseguranca;
	LinearLayout laysobre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajustes);
		context=this;
		overridePendingTransition(R.anim.filho_entrando, R.anim.main_saindo);
		//setTheme(R.style.AppTheme);

		mToolbar = findViewById(R.id.toolbar);
		mToolbar.setTitle("Ajustes");
		//mToolbar.setSubtitle("subtitulo");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		sp = getSharedPreferences(rotinas.cfg, Context.MODE_PRIVATE);
		carregatela();
	}

	public void carregatela(){
		montalaycomecardozero();
		monta_lay_copia_seguranca();
		montalaysobre();
	}

	private void montalaycomecardozero(){
		laycomecardozero = findViewById(R.id.laycomecardozero);
		laycomecardozero.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
				View dialogView = LayoutInflater.from(context).inflate(R.layout.confirma_comecardozero, null);

				final EditText confirma = dialogView.findViewById(R.id.confirma);
				Button btnnao =  dialogView.findViewById(R.id.btnnao);
				Button btnsim = dialogView.findViewById(R.id.btnsim);

				btnsim.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (confirma.getText().toString().trim().toLowerCase().equals("recomeçar")) {
							BancoSQLite db = new BancoSQLite(getApplicationContext());
							db.zerabanco();
							db.close();
							Intent intent = new Intent();
							setResult(1, intent);
							dialogBuilder.dismiss();
							finish();
						}
					}
				});
				btnnao.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// DO SOMETHINGS
						dialogBuilder.dismiss();
					}
				});

				dialogBuilder.setView(dialogView);
				dialogBuilder.show();
			}
		});
	}

	private void monta_lay_copia_seguranca(){
		laycopiaseguranca = findViewById(R.id.laycopiaseguranca);

		laycopiaseguranca.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, actAjustesBD.class));

//				LayoutInflater inflater = getLayoutInflater();
//				final View dialoglayout = inflater.inflate(R.layout.activity_ajustes_copiaseguranca, null);
//				AlertDialog.Builder builder = new AlertDialog.Builder(context);
//				builder.setView(dialoglayout);
//				builder.setTitle("Cópia de Segurança");
//				final AlertDialog ad = builder.show();
//
//				final Button btnexportaBD = dialoglayout.findViewById(R.id.btn_exportaBD);
//				btnexportaBD.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//
//						LayoutInflater inflater = getLayoutInflater();
//						final View dialoglayout = inflater.inflate(R.layout.activity_ajustes_copiaseguranca_exportabd, null);
//						AlertDialog.Builder alert = new AlertDialog.Builder(context);
//						alert.setCancelable(false);
//
//						alert.setView(dialoglayout);
//						alert.setTitle("Exporta Banco de Dados");
//						final AlertDialog ad = alert.show();
//
//						EditText txtnomearquivo = dialoglayout.findViewById(R.id.txtnomearquivo);
//						final Switch chkenvemail = dialoglayout.findViewById(R.id.chkenvemail);
//
//						Button btnsalvar = dialoglayout.findViewById(R.id.btnsalvar);
//						btnsalvar.setOnClickListener(new View.OnClickListener() {
//							@Override
//							public void onClick(View v) {
////								if(txtnomearquivo.getText().equals("") )
////									return;
//								new CriaBackup( context ).executeLocal( txtnomearquivo.getText().toString(), chkenvemail.isChecked() );
//
//
//								ad.dismiss();
//
//							}
//						});
//
//
//					}
//				});
//
//				final Button btnhorabackup = dialoglayout.findViewById(R.id.btnhorabackup);
//				btnhorabackup.setText(sp.getString(rotinas.cfg_keyhorabkp, "23:00"));
//
//				final Switch chkbackup = dialoglayout.findViewById(R.id.chkbackup);
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
			}
		});
	}

	private void montalaysobre() {
		laysobre = findViewById(R.id.laysobre);

		laysobre.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
				View dialogView = LayoutInflater.from(context).inflate(R.layout.activity_ajustes_sobre, null);

				TextView vs = findViewById(R.id.lblversao);

//				try {
//					vs.setText(context.getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
//				} catch (PackageManager.NameNotFoundException e) {
//					e.printStackTrace();
//				}


				Button btnvoltar = dialogView.findViewById(R.id.btnvoltar);

				btnvoltar.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						dialogBuilder.dismiss();

					}
				});


				dialogBuilder.setView(dialogView);
				dialogBuilder.show();
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
	}
}
