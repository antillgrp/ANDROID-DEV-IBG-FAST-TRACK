package com.gerson.droidcafeadaptative.ui.ui_00_home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gerson.droidcafeadaptative.BR;
import com.gerson.droidcafeadaptative.R;
import com.gerson.droidcafeadaptative.databinding.ViewholderCartProdDbBinding;
import com.gerson.droidcafeadaptative.model.ProductDescriptor;
import com.gerson.droidcafeadaptative.model.VendingMachine;

import java.util.Map;

public  class
		ShCartRecycleViewAdapter
	extends
		RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private final VendingMachine mVendingMachine;

	public ShCartRecycleViewAdapter(
		VendingMachine vendingMachine
	) {
		this.mVendingMachine = vendingMachine;
	}

	@Override
	public int getItemCount() {
		return
			this.mVendingMachine
				.getShCartProductMapCollection()
				.size();
	}

	private ViewholderCartProdDbBinding cartProdDbBinding;

	@Override
	public @NonNull RecyclerView.ViewHolder onCreateViewHolder(
		@NonNull ViewGroup parent,
		int viewType
	) {
		return new RecyclerView.ViewHolder(
			(
				//https://stackoverflow.com/a/51894195
				this.cartProdDbBinding =
					ViewholderCartProdDbBinding
					.inflate(
						LayoutInflater.from(parent.getContext()),
						parent,
						false
					)
			)
			.getRoot()
		){};
	}

	@Override
	public void onBindViewHolder(
		@NonNull RecyclerView.ViewHolder holder,
		int position
	) {
		if(position < 0 || position >= this.getItemCount())
			throw new IndexOutOfBoundsException(
				"ShCartRecycleViewAdapter::onBindViewHolder -> position"
			);

		@SuppressWarnings("unchecked")
		ProductDescriptor<Integer> product =
			(ProductDescriptor<Integer>)
				mVendingMachine
					.getShCartProductMapCollection()
					.keySet()
					.toArray()
					[position];

		this.cartProdDbBinding.setVariable(
			BR.cartProductBinding,
			new CartProductBinding(product)
		);
	}

	public class
		CartProductBinding
	implements
		Map.Entry<ProductDescriptor<Integer>, Double>,
		View.OnClickListener
	{
		final ProductDescriptor<Integer> mProduct;

		CartProductBinding(ProductDescriptor<Integer> product) { this.mProduct = product; }
		@Override public ProductDescriptor<Integer> getKey() { return this.mProduct; }
		@Override public Double getValue() {
			return  mVendingMachine.getShCartProductMapCollection().get(this.mProduct);
		}
		@Override public Double setValue(Double value) { throw new UnsupportedOperationException(); }
		@Override public void onClick(View view) {
			switch (view.getId()) {
				case R.id.button_add:
					mVendingMachine.addToCartProductAmount(
						this.mProduct,
						this.mProduct.getUnity()
					);
					break;
				case R.id.button_sub:
					mVendingMachine.returnFromCartProductAmount(
						this.mProduct,
						this.mProduct.getUnity()
					);
					break;
				default:
					Log.d(":NOT_KNOWN_PRODUCT_IMG:", "Menu Item Id:" + view.getId());
			}
		}
	}
}