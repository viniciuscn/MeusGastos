package nichele.meusgastos.SectionedRecyclerView;

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

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {



	private Context context;
	private ArrayList<Transacao> extrato;

	public ItemRecyclerViewAdapter(Context context, ArrayList<Transacao> extrato) {
		this.context = context;
		this.extrato = extrato;
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sectioned_recyclerview_item_custom_row_layout, parent, false);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ItemViewHolder holder, int position) {
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
		holder.lbldescricao.setText(extrato.get(position).getDescricao());

		GregorianCalendar gc=new GregorianCalendar();
		String data = extrato.get(position).getData();
		gc.set(GregorianCalendar.YEAR, Integer.valueOf(data.subSequence(0,4).toString()));
		gc.set(GregorianCalendar.MONTH, Integer.valueOf(data.subSequence(5,7).toString())-1);
		gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(data.subSequence(8,10).toString()));
		holder.lbldata.setText(datautil.formatadata(gc.getTime(), "dddd, dd"));
		holder.lblfuncao.setText(extrato.get(position).getFuncao());
		holder.lblcodcategoria.setText(extrato.get(position).categoria.getCodigoString());
		holder.lblnomecategoria.setText("   "+extrato.get(position).categoria.getNome()+"   ");
		holder.lblcodconta.setText(extrato.get(position).conta.getCodigoString());
		holder.lblnomeconta.setText("   "+extrato.get(position).conta.getNome()+"   ");
		holder.lbldescricao.setText(extrato.get(position).getDescricao());
		holder.lblvalor.setText(rotinas.formatavalorBR(extrato.get(position).getValorString()));
		if (extrato.get(position).getFuncao().equals("E") && extrato.get(position).getQuitado().equals("S"))
			holder.lblvalor.setTextColor(context.getResources().getColor(R.color.verde));
		else if (extrato.get(position).getFuncao().equals("S") && extrato.get(position).getQuitado().equals("S"))
			holder.lblvalor.setTextColor(context.getResources().getColor(R.color.vermelho));
	}

	@Override
	public int getItemCount() {
		return extrato.size();
	}

	class ItemViewHolder extends RecyclerView.ViewHolder {
		LinearLayout frame;
		TextView lbldata;
		TextView lblfuncao;
		TextView lblcodconta;
		TextView lblnomeconta;
		TextView lblcodcategoria;
		TextView lblnomecategoria;
		TextView lbldescricao;
		TextView lblvalor;
		ItemViewHolder(View itemView) {
			super(itemView);
			frame = itemView.findViewById(R.id.item_frame);
			lbldata = itemView.findViewById(R.id.item_data);
			lblfuncao = itemView.findViewById(R.id.item_funcao);
			lblcodconta= itemView.findViewById(R.id.item_codconta);
			lblnomeconta= itemView.findViewById(R.id.item_nomeconta);
			lblcodcategoria= itemView.findViewById(R.id.item_codcategoria);
			lblnomecategoria= itemView.findViewById(R.id.item_nomecategoria);
			lbldescricao= itemView.findViewById(R.id.item_descricao);
			lblvalor= itemView.findViewById(R.id.item_valor);
		}
	}

}