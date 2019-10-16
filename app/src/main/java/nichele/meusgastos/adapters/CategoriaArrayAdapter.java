package nichele.meusgastos.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.R;
import nichele.meusgastos.actCategorias;
import nichele.meusgastos.actTransacoes_Manutencao;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.util.rotinas;

public class CategoriaArrayAdapter extends RecyclerView.Adapter<CategoriaArrayAdapter.ViewHolder> {

   private ArrayList<Categoria> categorias;
   static Context context;

   public CategoriaArrayAdapter(ArrayList<Categoria> itemList) {
      this.categorias = itemList;
   }

   @Override
   public int getItemCount() {
      return categorias.size();
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      context = parent.getContext();
      View view = LayoutInflater.from(context).inflate(R.layout.activity_lis_categorias_item, parent, false);
      ViewHolder myViewHolder = new ViewHolder(view);
      return myViewHolder;
   }

   // load data in each row element
   @Override
   public void onBindViewHolder(final ViewHolder holder, final int position) {
      holder.frame.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(context, actCategorias.class );
            //intent.putExtra("tipdado", (categorias.get(position).funcao.equals("E") ? TipoDado.entradas : TipoDado.saidas) );
            intent.putExtra("situacao", "ALT");
            rotinas.categoria =  categorias.get(position);
            context.startActivity(intent);
         }
      });
      holder.item.setText(categorias.get(position).nome);

   }

   // Static inner class to initialize the views of rows
   public static class ViewHolder extends RecyclerView.ViewHolder  {
      LinearLayout frame;
      TextView item;

      public ViewHolder(View itemView) {
         super(itemView);
         frame = itemView.findViewById(R.id.item_frame);
         item = itemView.findViewById(R.id.item_descricao);
      }
   }

   @Override
   public void onAttachedToRecyclerView(RecyclerView recyclerView) {
      super.onAttachedToRecyclerView(recyclerView);
   }
}