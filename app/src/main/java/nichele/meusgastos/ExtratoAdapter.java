package nichele.meusgastos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import nichele.meusgastos.Classes.Transacao;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.util.datautil;
import nichele.meusgastos.util.rotinas;

public class ExtratoAdapter extends RecyclerView.Adapter<ExtratoAdapter.ExtratoViewHolder> {

    ArrayList<Transacao> extrato;
    static Context context;
    AlertDialog alerta;
    AlertDialog confirmacao;

    public ExtratoAdapter(ArrayList<Transacao> extrato){
        this.extrato = extrato;
    }

    @Override
    public ExtratoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transacoes_lista_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ExtratoViewHolder pvh = new ExtratoViewHolder(v);
        context = parent.getContext();
        return pvh;
    }

    @Override
    public void onBindViewHolder(ExtratoViewHolder holder, final int position) {

        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> itens = new ArrayList<>();
                itens.add("Alterar");
                itens.add("Excluir");

                //adapter utilizando um layout customizado (TextView)
                ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, itens);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //builder.setTitle("Qualifique este software:");
                //define o diálogo como uma lista, passa o adapter.
                builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (arg1==0) {
                            //Intent intent = new Intent(context, (extrato.get(position).funcao.equals("E") ? actRenda.class : actDespesas.class) );
                            Intent intent = new Intent(context, actTransacoes_Manutencao.class );
                            intent.putExtra("tipdado", (extrato.get(position).funcao.equals("E") ? TipoDado.entradas : TipoDado.saidas) );
                            intent.putExtra("situacao", "alt");
                            //intent.putExtra("registro", (Serializable) extrato.get(position));
                            rotinas.transacao =  extrato.get(position);
                            context.startActivity(intent);

                        }
                        else if (arg1==1){

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            //define o titulo
                            builder.setTitle("Confirmar");
                            //define a mensagem
                            builder.setMessage("Tem certeza que deseja excluir esta " + (extrato.get(position).funcao.equals("E") ? "receita" : "despesa") +"?");
                            //define um botão como positivo
                            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    BancoSQLite db = new BancoSQLite(context);
                                    db.deletatransacao(extrato.get(position).id);
                                    extrato.remove(position);
                                    //adapter.notifyDataSetChanged();
                                    confirmacao.dismiss();
                                }
                            });
                            //define um botão como negativo.
                            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    //Toast.makeText(MainActivity.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
                                    confirmacao.dismiss();
                                }
                            });
                            //cria o AlertDialog
                            confirmacao = builder.create();
                            //Exibe
                            confirmacao.show();
                        }

                        //Toast.makeText(context, "posição selecionada=" + arg1, Toast.LENGTH_SHORT).show();
                        alerta.dismiss();
                    }
                });

                alerta = builder.create();
                alerta.show();
            }
        });

        GregorianCalendar gc=new GregorianCalendar();
        String data = extrato.get(position).getData();
        gc.set(GregorianCalendar.YEAR, Integer.valueOf(data.subSequence(0,4).toString()));
        gc.set(GregorianCalendar.MONTH, Integer.valueOf(data.subSequence(5,7).toString())-1);
        gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(data.subSequence(8,10).toString()));

        holder.txtdata.setText(datautil.formatadata(gc.getTime(), "dddd, dd"));

        holder.txtfuncao.setText(extrato.get(position).getFuncao());
        holder.txtcodcategoria.setText(extrato.get(position).categoria.getCodigo());
        holder.txtnomecategoria.setText("   "+extrato.get(position).categoria.getNome()+"   ");
        holder.txtcodconta.setText(extrato.get(position).conta.getCodigo());
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


