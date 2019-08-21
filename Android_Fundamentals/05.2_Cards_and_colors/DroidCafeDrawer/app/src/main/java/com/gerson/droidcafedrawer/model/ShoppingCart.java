package com.gerson.droidcafedrawer.model;

import android.support.annotation.NonNull;
import java.util.HashMap;


public class ShoppingCart extends HashMap<ProductDescriptor, Double> {

    //https://stackoverflow.com/a/509115/11082043
    //http://help.eclipse.org/kepler/index.jsp?topic=/org.eclipse.jdt.doc.user/tasks/task-suppress_warnings.htm
    @SuppressWarnings("All")
    @Override
    public Double put(@NonNull ProductDescriptor productDescriptor, @NonNull Double amount){
        double result = this.getOrDefault(productDescriptor, 0d) + amount;
        if(result < 0d)
            throw new IllegalArgumentException("ShoppingCart::put -> (new amount + current amount) < 0");
        if(result == 0d)
            return this.remove(productDescriptor);
        else
            return super.put(productDescriptor, result);
    }
}


