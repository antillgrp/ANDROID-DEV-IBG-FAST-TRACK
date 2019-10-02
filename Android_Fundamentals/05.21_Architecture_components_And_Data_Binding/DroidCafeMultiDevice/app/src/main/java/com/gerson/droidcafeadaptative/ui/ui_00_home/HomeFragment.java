package com.gerson.droidcafeadaptative.ui.ui_00_home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.gerson.droidcafeadaptative.R;
import com.gerson.droidcafeadaptative.model.VendingMachineViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class HomeFragment extends Fragment {

	private View rootView;

	public View onCreateView(
		@NonNull LayoutInflater inflater,
	 	ViewGroup container,
		Bundle savedInstanceState
	) {
		this.rootView = inflater.inflate(R.layout.frag_00_home, container, false);

		//https://developer.android.com/topic/libraries/architecture/viewmodel

		// Creates a ViewModel the first time the system calls an activity's onCreate() method.
		// Re-created activities receive the same MyViewModel instance created by the first activity.

		//ViewModel requiring constructor parameters is instantiated via a custom factory class that
		//implements ViewModelProvider.Factory interface.
		// https://proandroiddev.com/architecture-components-modelview-livedata-33d20bdcc4e9

		VendingMachineViewModel vendingMachineViewModel =
		ViewModelProviders
		.of(
			this,
			VendingMachineViewModel.getFactory(
				Objects.requireNonNull(
					getActivity()
				)
				.getApplication()
			)
		)
		.get(VendingMachineViewModel.class);

		//Fragment parentFragment = this;

		vendingMachineViewModel
		.getProductsMenuLiveData()
		.observe( //ProductMenu does not change but it could in the future
		 	this,
			productsMenu -> {
				//**** UPDATE UI *****//
				((ViewPager)rootView.findViewById(R.id.view_pager))
					.setAdapter(
						new MenuPagerRvAdapter(
							getChildFragmentManager(),
							this //parentFragment
						)
					);
				((TabLayout)rootView.findViewById(R.id.tab_layout))
					.setupWithViewPager(rootView.findViewById(R.id.view_pager));
			}
		);

		//Subscribe this to the shopping cart update event to update the ui
		vendingMachineViewModel
		.getShoppingCartLiveData()
		.observe(
			this,
			shoppingCart -> {
				//**** UPDATE UI *****//
				LinearLayout cartLinearLayout = rootView.findViewById(R.id.cart_linlay);
				((LinearLayout.LayoutParams)cartLinearLayout.getLayoutParams())
					.weight = Math.min(0.10f * shoppingCart.size(), 0.5f);

				RecyclerView mRecyclerView;
				mRecyclerView = rootView.findViewById(R.id.cart_rv);
				mRecyclerView
					.setAdapter(
						new ShCartRecycleViewAdapter(
							vendingMachineViewModel.getVendingMachine()
						)
					);
				mRecyclerView
					.setLayoutManager(
						new LinearLayoutManager(rootView.getContext())
					);
			}
		);

		return rootView;
	}
	//endregion
}