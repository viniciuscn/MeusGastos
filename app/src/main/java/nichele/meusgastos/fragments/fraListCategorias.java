package nichele.meusgastos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nichele.meusgastos.BancoSQLite;
import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.R;
import nichele.meusgastos.adapters.CategoriaArrayAdapter;
import nichele.meusgastos.util.TipoDado;

public class fraListCategorias extends Fragment {

   TipoDado listar;
   private View view;

   public fraListCategorias(TipoDado plistar){
      listar = plistar;
   }

   public static fraListCategorias newInstance() {
      fraListCategorias fragment = new fraListCategorias();
      return fragment;
   }

   public fraListCategorias() {
      // Deve existir um construtor vazio
      // na classe que estende um Fragment
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      if (getArguments() != null) {
      }
   }


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.activity_lis_categorias_content_fragment, container, false);
      mostradados();
      return view;
   }

   @Override
   public void onStart() {
      super.onStart();
   }
   @Override
   public void onResume() {
      super.onResume();
      mostradados();
   }
   @Override
   public void onStop() {
      super.onStop();
   }
   @Override
   public void onDetach(){
      super.onDetach();
   }

   public void mostradados(){
      BancoSQLite db = new BancoSQLite(getContext());
      ArrayList<Categoria> lstcategorias = db.listacategorias(listar);
      db.close();
      CategoriaArrayAdapter rst = new CategoriaArrayAdapter(lstcategorias);
      RecyclerView recyclerView = view.findViewById(R.id.lista_dados);
      recyclerView.setAdapter(rst);
      recyclerView.setLayoutManager(new LinearLayoutManager( getContext()));
      recyclerView.setItemAnimator(new DefaultItemAnimator());
   }
}
