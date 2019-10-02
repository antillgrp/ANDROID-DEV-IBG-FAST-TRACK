package com.gerson.droidcafeadaptative.ui.ui_00_home;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gerson.droidcafeadaptative.R;
import com.gerson.droidcafeadaptative.model.ProductDescriptor;
import com.gerson.droidcafeadaptative.model.VendingMachineViewModel;

import java.util.LinkedHashSet;
import java.util.Objects;

public class MenuRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

	private final Fragment mParentFragment;
	private final LayoutInflater layoutInflater;
	private final LinkedHashSet<ProductDescriptor<Integer>> mProductSet;


	public MenuRecycleViewAdapter(
		@NonNull Fragment parentFragment,
		@NonNull LinkedHashSet<ProductDescriptor<Integer>> productSet
	) {
		this.mParentFragment = parentFragment;
		this.layoutInflater = LayoutInflater.from(parentFragment.getContext());
		this.mProductSet = productSet;
	}

	@Override
	public int getItemCount() {
		return this.mProductSet.size();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
		return new RecyclerView.ViewHolder(
			this.layoutInflater.inflate(
				R.layout.viewholder_menu_prod,
				parent,
				false
			)
		){};
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

		@SuppressWarnings("unchecked")
		ProductDescriptor<Integer> mProdD = (ProductDescriptor<Integer>)this.mProductSet.toArray()[position];

		//TODO : DataBinding

		ImageView imageViewProdImg = holder.itemView.findViewById(R.id.product_img);
		Glide
			.with(holder.itemView.getContext())
			.load(mProdD.getTag())
			.into( imageViewProdImg );

		/*imageViewProdImg.setOnClickListener(
			view -> {
				//https://medium.com/@suragch/creating-a-custom-alertdialog-bae919d2efa5
				// create an alert builder
				AlertDialog.Builder builder = new AlertDialog.Builder(
					holder.itemView.getContext()
				);

				//builder.setTitle(mProdD.name);

				View card = this.layoutInflater.inflate(
					R.layout.viewholder_menu_prod,
					null
				);

				builder.setView(card);

				TextView l_textViewProdName = card.findViewById(R.id.product_name);
				ImageView l_imageViewProdImg = card.findViewById(R.id.product_img);
				TextView l_textViewProdInfo = card.findViewById(R.id.product_info);
				LinearLayout l_linearLayout = card.findViewById(R.id.toolbar);

				l_textViewProdName.setText(mProdD.name);
				l_textViewProdInfo.setText(mProdD.info);
				Glide
					.with(card.getContext())
					.load(mProdD.getImageResource())
					.into(l_imageViewProdImg);
				l_linearLayout.setVisibility(LinearLayout.GONE);

				// create and show the alert dialog
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		);*/

		TextView textViewProdName = holder.itemView.findViewById(R.id.product_name);
		textViewProdName.setText(mProdD.name);

		TextView textViewProdInfo = holder.itemView.findViewById(R.id.product_info);
		textViewProdInfo.setText(mProdD.info);

		ImageButton imgButtonBuy = holder.itemView.findViewById(R.id.button_buy);
		imgButtonBuy.setOnClickListener(
			view -> ViewModelProviders
					.of(Objects.requireNonNull(mParentFragment))
					.get(VendingMachineViewModel.class)
					.getVendingMachine()
					.addToCartProductAmount( mProdD, mProdD.getUnity())
		);
	}
}