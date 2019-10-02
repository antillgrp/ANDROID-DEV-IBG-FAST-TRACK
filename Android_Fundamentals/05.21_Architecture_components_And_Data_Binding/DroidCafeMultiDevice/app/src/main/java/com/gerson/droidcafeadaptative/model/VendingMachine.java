package com.gerson.droidcafeadaptative.model;

import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableMap;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;


public class VendingMachine { //TODO make it singleton


	//region getMenuProductMapCollection
	protected final MapToObservableMapAdapter
	<
		ProductDescriptor<Integer>,
		Double,
		Map<ProductDescriptor<Integer>, Double>
	>
	productsMenu = new MapToObservableMapAdapter<>(
		//TODO: compute instead of inheritance (DONE)
		new LinkedHashMap<>() //order matters
	);

	public final MapToObservableMapAdapter
	<
		ProductDescriptor<Integer>,
		Double,
		Map<ProductDescriptor<Integer>, Double>
	>
	getMenuProductMapCollection() { return this.productsMenu; }
	//endregion

	//region getShCartProductMapCollection
	protected final MapToObservableMapAdapter
	<
		ProductDescriptor<Integer>,
		Double,
		Map<ProductDescriptor<Integer>, Double>
	>
	shoppingCart = new MapToObservableMapAdapter<>(
		//TODO: compute instead of inheritance
		new ArrayMap<>() //order does not matter
	);

	public final MapToObservableMapAdapter
	<
		ProductDescriptor<Integer>,
		Double,
		Map<ProductDescriptor<Integer>, Double>
	>
	getShCartProductMapCollection() { return this.shoppingCart; }
	//endregion

	//region getProductByCategoryMapCollection
	protected LinkedHashMap
	<
		String,
		LinkedHashSet<ProductDescriptor<Integer>>
	>
	productsByCategory = new LinkedHashMap<>(); //-> keeps insertion order

	public LinkedHashMap
	<
		String,
		LinkedHashSet<ProductDescriptor<Integer>>
	>
	getProductByCategoryMapCollection()
	{
		return this.productsByCategory;
	}
	//endregion

	public VendingMachine()
	{
		this.productsMenu
			.addOnMapChangedCallback(
				new ObservableMap.OnMapChangedCallback
				<
					ObservableMap<ProductDescriptor<Integer>, Double>,
					ProductDescriptor<Integer>,
					Double
				>()
				{
					int prevSize = 0;

					@Override
					public void onMapChanged(
						ObservableMap<ProductDescriptor<Integer>, Double> sender,
						ProductDescriptor<Integer> key
					){
						//only with add or delete
						if(prevSize == sender.size()) return;

						this.updateProductsByCategory(sender);

						prevSize = sender.size();
					}

					private void updateProductsByCategory(
						ObservableMap<ProductDescriptor<Integer>, Double> sender
					) {
						productsByCategory.clear();

						sender.forEach(
							(product, stock) ->
							{
								/*
									//https://docs.oracle.com/javase/6/docs/api/java/util/concurrent/ConcurrentHashMap.html#putIfAbsent%28K,%20V%29
									Returns:
									the previous value associated with the specified key,
									or null if there was no mapping for the key
								*/
								productsByCategory
									.putIfAbsent(
									product.category,
									productsByCategory
										.getOrDefault(
										product.category,
										new LinkedHashSet<>(
											//https://stackoverflow.com/a/26039382
											//Collections.singletonList(pd)
										)
									)
								);

								//noinspection ConstantConditions
								productsByCategory
									.get(product.category)
									.add(product);
							}
						);
					}
				}
			);
	}

	public void addToCartProductAmount
	(
		@NonNull ProductDescriptor<Integer> product,
		@NonNull Double amount
	) {
		if (!productsMenu.containsKey(product))
			throw new IllegalArgumentException("productDescriptor");

		if (amount <= 0 || amount > Objects.requireNonNull(productsMenu.get(product)))
			throw new IllegalArgumentException("amount");

		if (amount.equals(Objects.requireNonNull(productsMenu.get(product))))
			productsMenu.remove(product);
		else
			this.productsMenu.compute(
				product,
				(prod, stock) ->
					Objects.requireNonNull(stock) - amount
			);

		//add it to the shopping cart
		this.shoppingCart.compute(
			product,
			(prod, stock) ->
				(Objects.isNull(stock) ? 0 : Objects.requireNonNull(stock)) + amount
		);
	}

	public void returnFromCartProductAmount
	(
		@NonNull ProductDescriptor<Integer> product,
		@NonNull Double amount
	){
		if (!shoppingCart.containsKey(product))
			throw new IllegalArgumentException("productDescriptor");

		if (amount <= 0 || amount > Objects.requireNonNull(shoppingCart.get(product)))
			throw new IllegalArgumentException("amount");

		if (amount.equals(Objects.requireNonNull(shoppingCart.get(product))))
			shoppingCart.remove(product);
		else
			this.shoppingCart.compute(
				product,
				(prod, stock) ->
					Objects.requireNonNull(stock) - amount
			);

		//return
		this.productsMenu.compute(
			product,
			(prod, stock) ->
				(Objects.isNull(stock) ? 0 : Objects.requireNonNull(stock)) + amount
		);
	}
}
