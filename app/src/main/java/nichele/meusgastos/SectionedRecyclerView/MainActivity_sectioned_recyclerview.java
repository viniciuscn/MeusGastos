package nichele.meusgastos.SectionedRecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity_sectioned_recyclerview extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main_sectioned_recyclerview);

	}


	public void linearSectionedRecyclerViewVertical(View view) {
		startRecyclerViewActivity(RecyclerViewType.LINEAR_VERTICAL);
	}

	public void linearSectionedRecyclerViewHorizontal(View view) {
		startRecyclerViewActivity(RecyclerViewType.LINEAR_HORIZONTAL);
	}

	public void gridSectionedRecyclerView(View view) {
		startRecyclerViewActivity(RecyclerViewType.GRID);
	}

	private void startRecyclerViewActivity(RecyclerViewType recyclerViewType) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(RecyclerViewActivity.RECYCLER_VIEW_TYPE, recyclerViewType);
		startActivity(new Intent(this, RecyclerViewActivity.class).putExtras(bundle));
	}
}
