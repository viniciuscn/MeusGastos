package nichele.meusgastos.exemplo_recview_group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nichele.meusgastos.R;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LAYOUT_HEADER= 0;
    private static final int LAYOUT_CHILD= 1;

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<iRelatorio> listItemArrayList;

    public CustomAdapter(Context context,ArrayList<iRelatorio> listItemArrayList){

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listItemArrayList = listItemArrayList;
    }

    @Override
    public int getItemCount() {
        return listItemArrayList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if(listItemArrayList.get(position).isHeader()) {
            return LAYOUT_HEADER;
        }else
            return LAYOUT_CHILD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        if(viewType==LAYOUT_HEADER){
            View view = inflater.inflate(R.layout.exemplo_rv_header, parent, false);
            holder = new MyViewHolderHeader(view);
        }else {
            View view = inflater.inflate(R.layout.exemplo_rv_header_child, parent, false);
            holder = new MyViewHolderChild(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder.getItemViewType()== LAYOUT_HEADER)
        {
            MyViewHolderHeader vaultItemHolder = (MyViewHolderHeader) holder;
            vaultItemHolder.tvHeader.setText(listItemArrayList.get(position).getName());
        }
        else {

            MyViewHolderChild vaultItemHolder = (MyViewHolderChild) holder;
            vaultItemHolder.tvchild.setText(listItemArrayList.get(position).getName());

        }

    }


    class MyViewHolderHeader extends RecyclerView.ViewHolder{

        TextView tvHeader;

        public MyViewHolderHeader(View itemView) {
            super(itemView);

            tvHeader = itemView.findViewById(R.id.tvHeader);
        }

    }

    class MyViewHolderChild extends RecyclerView.ViewHolder{

        TextView tvchild;

        public MyViewHolderChild(View itemView) {
            super(itemView);

            tvchild = itemView.findViewById(R.id.tvChild);
        }

    }

}