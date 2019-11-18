package nichele.meusgastos.SectionedRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import nichele.meusgastos.Classes.Transacao;
import nichele.meusgastos.R;
import nichele.meusgastos.util.datautil;
import nichele.meusgastos.util.rotinas;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {



	private Context context;
	private ArrayList<Transacao> arrayList;

	public ItemRecyclerViewAdapter(Context context, ArrayList<Transacao> arrayList) {
		this.context = context;
		this.arrayList = arrayList;
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sectioned_recyclerview_item_custom_row_layout, parent, false);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ItemViewHolder holder, int position) {
		holder.lbldescricao.setText(arrayList.get(position).getDescricao());

		GregorianCalendar gc=new GregorianCalendar();
		String data = arrayList.get(position).getData();
		gc.set(GregorianCalendar.YEAR, Integer.valueOf(data.subSequence(0,4).toString()));
		gc.set(GregorianCalendar.MONTH, Integer.valueOf(data.subSequence(5,7).toString())-1);
		gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(data.subSequence(8,10).toString()));
		holder.lbldata.setText(datautil.formatadata(gc.getTime(), "dddd, dd"));
		holder.lblfuncao.setText(arrayList.get(position).getFuncao());
		holder.lblcodcategoria.setText(arrayList.get(position).categoria.getCodigoString());
		holder.lblnomecategoria.setText("   "+arrayList.get(position).categoria.getNome()+"   ");
		holder.lblcodconta.setText(arrayList.get(position).conta.getCodigoString());
		holder.lblnomeconta.setText("   "+arrayList.get(position).conta.getNome()+"   ");
		holder.lbldescricao.setText(arrayList.get(position).getDescricao());
		holder.lblvalor.setText(rotinas.formatavalorBR(arrayList.get(position).getValorString()));
		if (arrayList.get(position).getFuncao().equals("E") && arrayList.get(position).getQuitado().equals("S"))
			holder.lblvalor.setTextColor(context.getResources().getColor(R.color.verde));
		else if (arrayList.get(position).getFuncao().equals("S") && arrayList.get(position).getQuitado().equals("S"))
			holder.lblvalor.setTextColor(context.getResources().getColor(R.color.vermelho));
	}

	@Override
	public int getItemCount() {
		return arrayList.size();
	}

	class ItemViewHolder extends RecyclerView.ViewHolder {
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