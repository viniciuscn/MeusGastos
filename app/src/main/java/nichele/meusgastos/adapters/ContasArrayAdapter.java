package nichele.meusgastos.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nichele.meusgastos.Classes.Conta;
import nichele.meusgastos.R;
import nichele.meusgastos.actContas;
import nichele.meusgastos.util.rotinas;

public class ContasArrayAdapter extends RecyclerView.Adapter<ContasArrayAdapter.ViewHolder> {

   ArrayList<Conta> contas;
   static Context context;

   public ContasArrayAdapter(ArrayList<Conta> itemList) {
      this.contas = itemList;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      context = parent.getContext();
      View view = LayoutInflater.from(context).inflate(R.layout.activity_lis_categorias_item, parent, false);
      ViewHolder myViewHolder = new ViewHolder(view);
      return myViewHolder;
   }

   @Override
   public void onBindViewHolder(final ViewHolder holder, final int position) {
      final Conta cnt = contas.get(position);
      holder.frame.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(context, actContas.class);
            //intent.putExtra("tipdado", (categorias.get(position).funcao.equals("E") ? TipoDado.entradas : TipoDado.saidas) );
            intent.putExtra("situacao", "ALT");
            rotinas.conta = cnt;
            context.startActivity(intent);
         }
      });
      holder.item.setText(cnt.nome);

   }

   @Override
   public int getItemCount() {
      return contas.size();
   }

   public static class ViewHolder extends RecyclerView.ViewHolder  {
      LinearLayout frame;
      ImageView img;
      TextView item;

      public ViewHolder(View itemView) {
         super(itemView);
         frame = itemView.findViewById(R.id.item_frame);
         img = itemView.findViewById(R.id.img);
         item = itemView.findViewById(R.id.item_descricao);
      }
   }

   @Override
   public void onAttachedToRecyclerView(RecyclerView recyclerView) {
      super.onAttachedToRecyclerView(recyclerView);
   }
}






















