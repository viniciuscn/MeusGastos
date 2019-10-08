package nichele.meusgastos.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import nichele.meusgastos.R;
import nichele.meusgastos.rotinas;

public class fraVisaoGeral extends Fragment  {

   Toolbar toolbar;
   TextView textview;

   public fraVisaoGeral() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_visaogeral, container, false);
      toolbar = getActivity().findViewById(R.id.toolbar);
      //toolbar.setTitle("Visão Geral");
      definecores();

      textview = view.findViewById(R.id.txtsaldo);

      rotinas.animateTextView(0,200, textview);

      Button cmd = view.findViewById(R.id.cmdrodanumeros);
      cmd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            rotinas.animateTextView(0,200.50f,textview);
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
      //toolbar.setBackgroundColor( getResources().getColor(R.color.colorPrimary));
   }

   @Override
   public void onAttach(Context context) {
      super.onAttach(context);

   }

   @Override
   public void onDetach() {
      super.onDetach();
   }




}
