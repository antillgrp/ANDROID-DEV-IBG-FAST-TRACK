package com.gerson.droidcafedrawer.model;

import android.support.annotation.NonNull;
import java.util.HashMap;


public class ShoppingCart extends HashMap<ProductDescriptor, Double> {

    @Override
    public Double put(@NonNull ProductDescriptor productDescriptor, @NonNull Double amount){
        double result = this.getOrDefault(productDescriptor, 0d) + amount;
        if(result < 0d)
            throw new IllegalArgumentException("ShoppingCart:put: Amount + Current <= 0");
        if(result == 0d)
            return this.remove(productDescriptor);
        else
            return super.put(productDescriptor, result);
    }
}


