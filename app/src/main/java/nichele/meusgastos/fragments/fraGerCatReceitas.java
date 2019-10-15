package nichele.meusgastos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import nichele.meusgastos.R;

public class fraGerCatReceitas extends Fragment {

   private View view;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      view = inflater.inflate(R.layout.fragment_transacoes, container, false);

      return view;
   }

}
