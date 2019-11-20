package nichele.meusgastos.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Date;

import nichele.meusgastos.BancoSQLite;
import nichele.meusgastos.MainActivity;
import nichele.meusgastos.R;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.util.datautil;
import nichele.meusgastos.util.rotinas;

public class fraVisaoGeral extends Fragment  {

	Toolbar toolbar;
	private View view;

	public fraVisaoGeral() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_visaogeral, container, false);
		toolbar = getActivity().findViewById(R.id.toolbar);
		//toolbar.setTitle("Visão Geral");
		definecores();

		//textview = view.findViewById(R.id.txtsaldo);

		//rotinas.animateTextView(0,200, textview);


//      cmd.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            rotinas.animateTextView(0,200.50f,textview);
//         }
//      });

		montacardvalores();

		EditText limite = view.findViewById(R.id.limite);
		EditText consumo = view.findViewById(R.id.consumo);

		View vlimite = view.findViewById(R.id.vlimite);
		View vconsumo = view.findViewById(R.id.vconsumo);
		vconsumo.setLayoutParams(new LinearLayout.LayoutParams(0, 30));

		Button cmdaplica_limite = view.findViewById(R.id.cmdaplica_limite);
		cmdaplica_limite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		Button cmdaplicar_consumo = view.findViewById(R.id.cmdaplicar_consumo);
		cmdaplicar_consumo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});


		return view;
	}

	private void definecores(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getActivity().getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(  getResources().getColor(R.color.colorPrimaryDark));
		}
		toolbar.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
	}


	private void  montacardvalores(){
		TextView card_mes = view.findViewById(R.id.card_mes);
		TextView tvsldanterior = view.findViewById(R.id.card_sldanterior);
		RelativeLayout layE = view.findViewById(R.id.layE);
		TextView tvreceitas = view.findViewById(R.id.card_vlrreceitas);
		RelativeLayout layS = view.findViewById(R.id.layS);
		TextView tvdespesas = view.findViewById(R.id.card_vlrdespesas);
		TextView tvsldrd = view.findViewById(R.id.card_sldrd);
		TextView tvsldfinal = view.findViewById(R.id.card_sldfinal);

		card_mes.setText(datautil.formatadata(new Date(),"mmm, yy"));


		BancoSQLite db = new BancoSQLite(getContext());
		String datinicial = datautil.primeirodiadomes(new Date());
		String datfinal = datautil.ultimodiadomes(new Date());

		float sldanterior = db.buscavalores(TipoDado.sldanterior, datinicial, "");
		float receitas = db.buscavalores(TipoDado.entradas, datinicial, datfinal);
		float despesas = db.buscavalores(TipoDado.saidas, datinicial, datfinal);
		float sldrd = receitas - despesas;
		float sldatual = sldanterior+sldrd;
		db.close();

		tvsldanterior.setText(rotinas.formatavalorBR(sldanterior));
		tvreceitas.setText(rotinas.formatavalorBR(receitas));
		tvdespesas.setText(rotinas.formatavalorBR(despesas));
		tvsldrd.setText(rotinas.formatavalorBR(sldrd));
		tvsldfinal.setText(rotinas.formatavalorBR(sldatual));
		if (sldatual < 0)
			tvsldfinal.setTextColor(getResources().getColor(R.color.vermelho));
		else
			tvsldfinal.setTextColor(getResources().getColor(R.color.verde));

		layE.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).fabmenu.collapse();
				((MainActivity)getActivity()).navigationDrwarerLeft.setSelection(1);
				((MainActivity)getActivity()).abrefragment(new fraTransacoes(TipoDado.entradas));
			}
		});
		layS.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).fabmenu.collapse();
				((MainActivity)getActivity()).navigationDrwarerLeft.setSelection(1);
				((MainActivity)getActivity()).abrefragment(new fraTransacoes(TipoDado.saidas));
			}
		});
	}

	@Override
	public void onAttach(Context context) { super.onAttach(context); }

	@Override
	public void onResume() {
		super.onResume();
		montacardvalores();
	}

	@Override
	public void onDetach() { super.onDetach(); }


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
		//if (requestCode == RC_SIGN_IN) {
		// The Task returned from this call is always completed, no need to attach
		// a listener.

		//}
	}


}
