package com.gerson.droidcafeadaptative.ui.ui_00_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gerson.droidcafeadaptative.R;
import com.gerson.droidcafeadaptative.model.ProductDescriptor;
import com.gerson.droidcafeadaptative.model.VendingMachineViewModel;

import java.util.LinkedHashSet;
import java.util.Objects;

public class MenuPageFragment extends Fragment {

	public static Fragment newInstance(String category) {

		Bundle resultBundle = new Bundle();
		resultBundle.putString("getPageTitle", category);

		MenuPageFragment result = new MenuPageFragment();
		result.setArguments(resultBundle);

		return result;
	}

	public MenuPageFragment(){ super(); }


	@Override
	public View onCreateView(
		@NonNull LayoutInflater inflater,
		ViewGroup container,
		Bundle savedInstanceState
	) {
		View rootView = inflater.inflate(
			R.layout.frag_00_home_menu_page,
			container,
			false
		);

		if( this.getArguments() == null || !this.getArguments().containsKey("getPageTitle"))
			return  rootView;

		Fragment parent = super.getParentFragment();

		ViewModelProviders
		.of(parent)
		.get(VendingMachineViewModel.class)
		.getProductsMenuLiveData()
		.observe(
			this,
			productsMenu ->
			{
				if(Objects.isNull(parent)) return;

				//UPDATE UI
				RecyclerView mRecyclerView;
				mRecyclerView = rootView.findViewById(R.id.recyclerv_menu_entries);
				mRecyclerView.setLayoutManager(
					new GridLayoutManager(
						getContext(),
						getResources().getInteger(R.integer.grid_column_count)
					)
				);
				mRecyclerView.setAdapter(
					new MenuRecycleViewAdapter(
						parent,
						this.getProdSetFromCategory(
							parent,
							getArguments().getString("getPageTitle")
						)
					)
				);
			}
		);

		/*final RecyclerView mRecyclerView = rootView.findViewById(R.id.recyclerv_menu_entries);

		int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

		//mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mRecyclerView.setLayoutManager(
			new GridLayoutManager(getContext(),gridColumnCount )
		);

		if(this.getArguments() != null && this.getArguments().containsKey(SERIALIZABLE_PROD_LIST)){

			Serializable sprods = this.getArguments().getSerializable(SERIALIZABLE_PROD_LIST);

			//http://www.informit.com/articles/article.aspx?p=2861454&seqNum=2
			@SuppressWarnings("unchecked")
			final List<MenuProductDescriptor> cprods = (List<MenuProductDescriptor>)sprods;

			mRecyclerView.setAdapter(
				new MenuRecycleViewAdapter(container.getContext(),cprods )
			);

			ItemTouchHelper helper = new ItemTouchHelper(
				new ItemTouchHelper.SimpleCallback(
					ItemTouchHelper.UP | ItemTouchHelper.DOWN
							|
							ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
					gridColumnCount == 1 ? ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT : 0
				) {
					@Override
					public boolean onMove(
						@NonNull RecyclerView recyclerView,
						@NonNull RecyclerView.ViewHolder viewHolder,
						@NonNull RecyclerView.ViewHolder target
					) {
						int from = viewHolder.getAdapterPosition();
						int to = target.getAdapterPosition();
						Collections.swap(cprods, from, to);
						mRecyclerView.getAdapter().notifyItemMoved(from, to);
						return true;
					}

					@Override
					public void onSwiped(
						@NonNull RecyclerView.ViewHolder viewHolder,
						int direction
					) {
						cprods.remove(viewHolder.getAdapterPosition());
						mRecyclerView
						.getAdapter()
						.notifyItemRemoved(
							viewHolder.getAdapterPosition()
						);
					}
				}
			);

			helper.attachToRecyclerView(mRecyclerView);
		}*/

		return rootView;
	}

	private LinkedHashSet<ProductDescriptor<Integer>> getProdSetFromCategory(Fragment parent, String category)
	{
		return
			ViewModelProviders
			.of(parent)
			.get(VendingMachineViewModel.class)
			.getVendingMachine()
			.getProductByCategoryMapCollection()
			.get(category);
	}
}