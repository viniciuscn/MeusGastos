package nichele.meusgastos.SectionedRecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nichele.meusgastos.R;

public class RecyclerViewActivity extends AppCompatActivity {

	protected static final String RECYCLER_VIEW_TYPE = "recycler_view_type";
	private RecyclerViewType recyclerViewType;
	private RecyclerView recyclerView;


	List<String> formatos = new ArrayList<>(Arrays.asList("Linear Sectioned RecyclerView (Vertical)",
			"Linear Sectioned RecyclerView (Horizontal)",
			"Grid Sectioned RecyclerView"));

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sectioned_recycler_view_activity);

		//get enum type passed from MainActivity_sectioned_recyclerview
		recyclerViewType = (RecyclerViewType) getIntent().getSerializableExtra(RECYCLER_VIEW_TYPE);
		//setUpToolbarTitle();
		setUpRecyclerView();
		populateRecyclerView();
	}

	//set toolbar title and set back button
	private void setUpToolbarTitle() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		switch (recyclerViewType) {
			case LINEAR_HORIZONTAL:
				//getSupportActionBar().setTitle(getResources().getString(R.string.linear_sectioned_recyclerview_horizontal));
				getSupportActionBar().setTitle(formatos.get(0));
				break;
			case LINEAR_VERTICAL:
				//getSupportActionBar().setTitle(getResources().getString(R.string.linear_sectioned_recyclerview_vertical));
				break;
			case GRID:
				//getSupportActionBar().setTitle(getResources().getString(R.string.grid_sectioned_recyclerview));
				break;

		}
	}

	//setup recycler view
	private void setUpRecyclerView() {
		recyclerView = (RecyclerView) findViewById(R.id.sectioned_recycler_view);
		recyclerView.setHasFixedSize(true);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(linearLayoutManager);
	}

	//populate recycler view
	private void populateRecyclerView() {
		ArrayList<SectionModel> sectionModelArrayList = new ArrayList<>();
		//for loop for sections
		for (int i = 1; i <= 5; i++) {
			ArrayList<String> itemArrayList = new ArrayList<>();
			//for loop for items
			for (int j = 1; j <= 10; j++) {
				itemArrayList.add("Item " + j);
			}

			//add the section and items to array list
			sectionModelArrayList.add(new SectionModel("Section " + i, itemArrayList));
			//sectionModelArrayList.add(new SectionModel("Section b " + i, new ArrayList<String>()));
		}

		SectionRecyclerViewAdapter adapter = new SectionRecyclerViewAdapter(this, recyclerViewType, sectionModelArrayList);
		recyclerView.setAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}
