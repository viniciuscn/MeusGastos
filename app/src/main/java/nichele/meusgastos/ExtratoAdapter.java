package nichele.meusgastos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import nichele.meusgastos.Classes.Transacao;

public class ExtratoAdapter extends RecyclerView.Adapter<ExtratoAdapter.ExtratoViewHolder> {

    ArrayList<Transacao> extrato;
    static Context context;

    ExtratoAdapter(){}

    public ExtratoAdapter(ArrayList<Transacao> extrato, Context context){
        this.extrato = extrato;
        this.context = context;
    }

    @Override
    public ExtratoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transacoes_lista_item, parent, false);
        ExtratoViewHolder pvh = new ExtratoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ExtratoViewHolder holder, final int position) {
        holder.txtdata.setText(extrato.get(position).data);
        holder.txtfuncao.setText(extrato.get(position).funcao);
        holder.txtcodconta.setText(extrato.get(position).conta.codigo);
        holder.txtnomeconta.setText(extrato.get(position).conta.nome);
        holder.txtcodcategoria.setText(extrato.get(position).categoria.codigo);
        holder.txtnomecategoria.setText(extrato.get(position).categoria.nome);
        holder.txtdescricao.setText(extrato.get(position).descricao);
        holder.txtvalor.setText(extrato.get(position).getValorString());
    }

    @Override
    public int getItemCount() {
        return extrato.size();
    }

    public static class ExtratoViewHolder extends RecyclerView.ViewHolder {
        TextView txtdata;
        TextView txtfuncao;
        TextView txtcodconta;
        TextView txtnomeconta;
        TextView txtcodcategoria;
        TextView txtnomecategoria;
        TextView txtdescricao;
        TextView txtvalor;
        ExtratoViewHolder(View itemView) {
            super(itemView);
            txtdata = itemView.findViewById(R.id.item_data);
            txtfuncao = itemView.findViewById(R.id.item_funcao);
            txtcodconta= itemView.findViewById(R.id.item_codconta);
            txtnomeconta= itemView.findViewById(R.id.item_nomeconta);
            txtcodcategoria= itemView.findViewById(R.id.item_codcategoria);
            txtnomecategoria= itemView.findViewById(R.id.item_nomecategoria);
            txtdescricao= itemView.findViewById(R.id.item_descricao);
            txtvalor= itemView.findViewById(R.id.item_valor);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}


