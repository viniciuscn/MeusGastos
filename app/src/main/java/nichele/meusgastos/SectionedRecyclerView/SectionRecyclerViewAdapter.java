package nichele.meusgastos.SectionedRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nichele.meusgastos.R;

public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<SectionRecyclerViewAdapter.SectionViewHolder> {



	private Context context;
	private RecyclerViewType recyclerViewType;
	private ArrayList<SectionModel> sectionModelArrayList;

	public SectionRecyclerViewAdapter(Context context,RecyclerViewType recyclerViewType, ArrayList<SectionModel> sectionModelArrayList) {
		this.context = context;
		this.recyclerViewType = recyclerViewType;
		this.sectionModelArrayList = sectionModelArrayList;
	}

	@Override
	public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sectioned_recyclerview_section_custom_row_layout, parent, false);
		return new SectionViewHolder(view);
	}

	@Override
	public void onBindViewHolder(SectionViewHolder holder, int position) {
		final SectionModel grupo = sectionModelArrayList.get(position);
		holder.header_texto.setText(grupo.getHeaderTexto());
		holder.header_saldo.setText(grupo.getSaldo());

		//recycler view for items
		holder.itemRecyclerView.setHasFixedSize(true);
		holder.itemRecyclerView.setNestedScrollingEnabled(false);

		/* set layout manager on basis of recyclerview enum type */
		switch (recyclerViewType) {
			case LINEAR_VERTICAL:
				LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
				holder.itemRecyclerView.setLayoutManager(linearLayoutManager);
				break;
			case LINEAR_HORIZONTAL:
				LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
				holder.itemRecyclerView.setLayoutManager(linearLayoutManager1);
				break;
			case GRID:
				GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
				holder.itemRecyclerView.setLayoutManager(gridLayoutManager);
				break;
		}
		//if (grupo.getLctos() != null) {
			ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(context, grupo.getLctos());
			holder.itemRecyclerView.setAdapter(adapter);
		//}
		//show toast on click of show all button
		holder.header_saldo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(context, "You clicked on Show All of : " + sectionModel.getheadertexto(), Toast.LENGTH_SHORT).show();
			}
		});


		holder.lblreceitas.setText(grupo.getReceitas());
		holder.lbldespesas.setText(grupo.getDespesas());
		holder.lblsaldo.setText(grupo.getSaldo());
	}

	@Override
	public int getItemCount() {
		return sectionModelArrayList.size();
	}

	class SectionViewHolder extends RecyclerView.ViewHolder {
		private TextView header_texto, header_saldo;
		private RecyclerView itemRecyclerView;

		TextView lblreceitas, lbldespesas, lblsaldo;


		public SectionViewHolder(View itemView) {
			super(itemView);
			header_texto = itemView.findViewById(R.id.section_label);
			header_saldo = itemView.findViewById(R.id.section_show_all_button);
			itemRecyclerView = itemView.findViewById(R.id.item_recycler_view);

			lblreceitas = itemView.findViewById(R.id.section_footer_vlrreceitas);
			lbldespesas = itemView.findViewById(R.id.section_footer_vlrdespesas);
			lblsaldo = itemView.findViewById(R.id.section_footer_vlrsaldo);
		}
	}
}
