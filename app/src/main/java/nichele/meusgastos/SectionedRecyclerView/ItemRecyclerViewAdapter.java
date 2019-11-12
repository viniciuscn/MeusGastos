package nichele.meusgastos.SectionedRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nichele.meusgastos.R;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {

	class ItemViewHolder extends RecyclerView.ViewHolder {
		private TextView itemLabel;

		public ItemViewHolder(View itemView) {
			super(itemView);
			itemLabel = (TextView) itemView.findViewById(R.id.item_label);
		}
	}

	private Context context;
	private ArrayList<String> arrayList;

	public ItemRecyclerViewAdapter(Context context, ArrayList<String> arrayList) {
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
		holder.itemLabel.setText(arrayList.get(position));
	}

	@Override
	public int getItemCount() {
		return arrayList.size();
	}


}