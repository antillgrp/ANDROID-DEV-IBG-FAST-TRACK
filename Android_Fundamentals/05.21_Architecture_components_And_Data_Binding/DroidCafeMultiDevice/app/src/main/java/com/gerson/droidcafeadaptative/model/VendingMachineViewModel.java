package com.gerson.droidcafeadaptative.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;

import java.math.BigDecimal;
import java.util.Map;


public class VendingMachineViewModel extends AndroidViewModel {

	//region ####STATIC####

	public static AndroidViewModelFactory getFactory(@NonNull Application application){
		return new ViewModelProvider.AndroidViewModelFactory(application);
	}

	//endregion

	//region VendingMachine
	private VendingMachine vendingMachine = new VendingMachine(); // sell access

	public VendingMachine getVendingMachine() { return this.vendingMachine; }
	//endregion

	//region ProductsMenuLiveData
	protected ObservableMapToLiveDataAdapter
	<
		ProductDescriptor<Integer>,
		Double,
		MapToObservableMapAdapter
		<
			ProductDescriptor<Integer>,
			Double,
			Map<ProductDescriptor<Integer>, Double>
		>
	>
	productsMenu_LD = new ObservableMapToLiveDataAdapter<>(
		this.vendingMachine.getMenuProductMapCollection()
	);

	public ObservableMapToLiveDataAdapter
	<
		ProductDescriptor<Integer>,
		Double,
		MapToObservableMapAdapter
		<
			ProductDescriptor<Integer>,
			Double,
			Map<ProductDescriptor<Integer>, Double>
		>
	>
	getProductsMenuLiveData() { return productsMenu_LD; }
	//endregion

	//region ShoppingCartLiveData
	protected ObservableMapToLiveDataAdapter
	<
		ProductDescriptor<Integer>,
		Double,
		MapToObservableMapAdapter
		<
			ProductDescriptor<Integer>,
			Double,
			Map<ProductDescriptor<Integer>, Double>
		>
	>
	shoppingCart_LD = new ObservableMapToLiveDataAdapter<>(
		this.vendingMachine.getShCartProductMapCollection()
	);

	public ObservableMapToLiveDataAdapter
	<
		ProductDescriptor<Integer>,
		Double,
		MapToObservableMapAdapter
		<
			ProductDescriptor<Integer>,
			Double,
			Map<ProductDescriptor<Integer>, Double>
		>
	>
	getShoppingCartLiveData() { return shoppingCart_LD; }
	//endregion

	/*
	{
		this.vendingMachine = new VendingMachine();

		this.productsMenu_LD = new ObservableMapToLiveDataAdapter<>(
			this.vendingMachine.getMenuProductMapCollection()
		);

		this.shoppingCart_LD = new ObservableMapToLiveDataAdapter<>(
			this.vendingMachine.getShCartProductMapCollection()
		);
	}
	*/

	public VendingMachineViewModel(@NonNull Application application)
	{
		super(application); //getApplication() to retrieve it

		this.loadProductsMenu();
	}

	private int nameToImgRes(String imgName) {
		return  this.getApplication().getApplicationContext().getResources().getIdentifier(
			imgName,
			"drawable",
			"com.gerson.droidcafeadaptative"
		);
	}

	private void loadProductsMenu() {
		//abbreviating
		Map<ProductDescriptor<Integer>,Double> productsMenu;
		productsMenu = this.vendingMachine.getMenuProductMapCollection();

		//region Cuban Burger
		productsMenu.put(
			new ProductDescriptor<>(
				ProductDescriptor.nextLongID(),
				"Cuban Burger",
				"Burger",
				"Cuban Sandwich Burger. " +
					"A simply seasoned ground pork patty combined with deli ham, " +
					"Swiss cheese and mustard on a crusty burger bun.",
				new BigDecimal("5"),
				nameToImgRes("img_cuban_burger")
			),
			Double.MAX_VALUE
		);
		//endregion

		//region Sriracha Burger
		productsMenu.put(
			new ProductDescriptor<>(
				ProductDescriptor.nextLongID(),
				"Sriracha Burger",
				"Burger",
				"A Peanut Butter Sriracha Bacon Cheeseburger combines sweet, " +
					"salty smoky and spicy flavours into a taste explosion of a burger.",
				new BigDecimal("5"),
				nameToImgRes("img_sriracha_burger")
			),
			Double.MAX_VALUE
		);
		//endregion

		//region Barbecue Burger
		productsMenu.put(
			new ProductDescriptor<>(
				ProductDescriptor.nextLongID(),
				"Barbecue Burger",
				"Burger",
				"Barbecue Bacon Cheddar Sliders. A terrific " +
					"way to feed the crowd at parties, cookouts, while tailgating " +
					"or just for a casual pot luck dinner.",
				new BigDecimal("5"),
				nameToImgRes("img_barbecue_burger")
			),
			Double.MAX_VALUE
		);
		//endregion

		//region "Donut"
		productsMenu.put(
			new ProductDescriptor<>(
				ProductDescriptor.nextLongID(),
				"Donut",
				"Dessert",
				"Donuts are glazed and sprinkled with candy.",
				new BigDecimal("5"),
				nameToImgRes("img_donut_circle")
			),
			Double.MAX_VALUE
		);
		//endregion

		//region "Froyo"
		productsMenu.put(
			new ProductDescriptor<>(
				ProductDescriptor.nextLongID(),
				"Froyo",
				"Dessert",
				"FroYo is premium self-serve frozen yogurt.",
				new BigDecimal("5"),
				nameToImgRes("img_froyo_circle")
			),
			Double.MAX_VALUE
		);
		//endregion

		//region "Ice Cream Sandwich"
		productsMenu.put(
			new ProductDescriptor<>(
				ProductDescriptor.nextLongID(),
				"Ice Cream Sandwich",
				"Dessert",
				"Ice cream sandwiches have chocolate wafers and vanilla filling.",
				new BigDecimal("5"),
				nameToImgRes("img_icecream_circle")
			),
			Double.MAX_VALUE
		);
		//endregion

		//region "Milk Shake"
		productsMenu.put(
			new ProductDescriptor<>(
				ProductDescriptor.nextLongID(),
				"Milk Shake",
				"Drink",
				"Made with ice cream, milk, and any additional flavors, " +
					"it’s a well-loved companion to a burger and fries " +
					"or simply enjoyed alone as sweet treat.",
				new BigDecimal("5"),
				nameToImgRes("img_milk_shake")
			),
			Double.MAX_VALUE
		);
		//endregion

		//region "Natural Juice"
		productsMenu.put(
			new ProductDescriptor<>(
				ProductDescriptor.nextLongID(),
				"Natural Juice",
				"Drink",
				"Juice is a drink made from the extraction or pressing of the " +
					"natural liquid contained in fruit and vegetables.",
				new BigDecimal("5"),
				nameToImgRes("img_nat_juice")
			),
			Double.MAX_VALUE
		);
		//endregion

		//region "Fountain Soda"
		productsMenu.put(
			new ProductDescriptor<>(
				ProductDescriptor.nextLongID(),
				"Fountain Soda",
				"Drink",
				"Your favorite ice cold soda anytime, at the push of a button, " +
					"carbonated flavors, and 1 non-carbonated, " +
					"plus you get Ice cold Soda Water, and Ice.",
				new BigDecimal("5"),
				nameToImgRes("img_fountain_soda")
			),
			Double.MAX_VALUE
		);
		//endregion
	}
}
