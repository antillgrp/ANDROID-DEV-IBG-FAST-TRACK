package com.gerson.droidcafedrawer.model;

import android.support.annotation.NonNull;

import java.math.BigDecimal;

public class InventoryProduct extends ProductDescriptor {

    protected double amount;

    public InventoryProduct(long id, @NonNull String name, @NonNull BigDecimal price, double amount) {
        super(id, name, price);
        this.setAmount(amount);
    }

    public InventoryProduct(ProductDescriptor productDescriptor, double amount) {
        this(
            productDescriptor.id,
            productDescriptor.name,
            productDescriptor.price,
            amount
        );
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) throws IllegalArgumentException {
        if(amount <= 0d) throw new IllegalArgumentException("Inventory product amount hast to be grater than 0");
        this.amount = amount;
    }
}
