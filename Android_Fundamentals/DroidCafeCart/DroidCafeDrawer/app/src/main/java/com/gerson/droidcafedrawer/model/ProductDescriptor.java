package com.gerson.droidcafedrawer.model;

import android.support.annotation.NonNull;

import java.math.BigDecimal;

public class ProductDescriptor {

    public final long id;

    public final String name;

    //http://www.javapractices.com/topic/TopicAction.do?Id=13
    protected BigDecimal price;

    public ProductDescriptor(long id, @NonNull String name, @NonNull BigDecimal price) {
        this.id = id;
        this.name = name;
        this.setPrice(price);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NonNull BigDecimal price) throws IllegalArgumentException {
        if(price.compareTo(new BigDecimal(0)) < 0) throw new IllegalArgumentException("price < 0");
        this.price = price;
    }

    @Override
    public boolean equals(@NonNull Object o){
        if(!(o instanceof ProductDescriptor)) return false;
        ProductDescriptor param = (ProductDescriptor) o;
        return
            this.id == param.id
            &&
            this.name.equals(param.name)
            &&
            this.price.compareTo(param.price) == 0;
    }

    @Override
    public int hashCode(){
        //Making sure two equal product descriptor have the same hashcode
        //Two equal strings have the same hashcode
        //https://javaconceptoftheday.com/when-to-use-equals-hashcode-on-strings/
        return this.name.hashCode()^(Long.valueOf(this.id).hashCode());
        //The same Longs have the same hash
        //https://docs.oracle.com/javase/7/docs/api/java/lang/Long.html#hashCode()
    }
}
