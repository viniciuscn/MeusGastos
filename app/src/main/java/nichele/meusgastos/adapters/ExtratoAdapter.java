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
import java.util.GregorianCalendar;

import nichele.meusgastos.Classes.Transacao;
import nichele.meusgastos.R;
import nichele.meusgastos.actTransacoes_Manutencao;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.util.datautil;
import nichele.meusgastos.util.rotinas;

public class ExtratoAdapter extends RecyclerView.Adapter<ExtratoAdapter.ExtratoViewHolder> {

    ArrayList<Transacao> extrato;
    static Context context;

    public ExtratoAdapter(ArrayList<Transacao> extrato){
        this.extrato = extrato;
    }

    @Override
    public ExtratoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        // create a new view
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_transacoes_lista_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ExtratoViewHolder pvh = new ExtratoViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(ExtratoViewHolder holder, final int position) {
        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, actTransacoes_Manutencao.class );
                intent.putExtra("tipdado", (extrato.get(position).funcao.equals("E") ? TipoDado.entradas : TipoDado.saidas) );
                intent.putExtra("situacao", "ALT");
                rotinas.transacao =  extrato.get(position);
                context.startActivity(intent);
            }
        });

        GregorianCalendar gc=new GregorianCalendar();
        String data = extrato.get(position).getData();
        gc.set(GregorianCalendar.YEAR, Integer.valueOf(data.subSequence(0,4).toString()));
        gc.set(GregorianCalendar.MONTH, Integer.valueOf(data.subSequence(5,7).toString())-1);
        gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(data.subSequence(8,10).toString()));

        holder.txtdata.setText(datautil.formatadata(gc.getTime(), "dddd, dd"));
        holder.txtfuncao.setText(extrato.get(position).getFuncao());
        holder.txtcodcategoria.setText(extrato.get(position).categoria.getCodigoString());
        holder.txtnomecategoria.setText("   "+extrato.get(position).categoria.getNome()+"   ");
        holder.txtcodconta.setText(extrato.get(position).conta.getCodigoString());
        holder.txtnomeconta.setText("   "+extrato.get(position).conta.getNome()+"   ");
        holder.txtdescricao.setText(extrato.get(position).getDescricao());
        holder.txtvalor.setText(rotinas.formatavalorBR(extrato.get(position).getValorString()));
        if (extrato.get(position).getFuncao().equals("E") && extrato.get(position).getQuitado().equals("S"))
            holder.txtvalor.setTextColor(context.getResources().getColor(R.color.verde));
        else if (extrato.get(position).getFuncao().equals("S") && extrato.get(position).getQuitado().equals("S"))
            holder.txtvalor.setTextColor(context.getResources().getColor(R.color.vermelho));
    }

    @Override
    public int getItemCount() {
        return extrato.size();
    }

    public static class ExtratoViewHolder extends RecyclerView.ViewHolder {
        LinearLayout frame;
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
            frame = itemView.findViewById(R.id.item_frame);
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