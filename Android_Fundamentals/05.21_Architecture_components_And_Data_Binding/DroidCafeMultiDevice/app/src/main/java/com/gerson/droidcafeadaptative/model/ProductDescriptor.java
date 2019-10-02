package com.gerson.droidcafeadaptative.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.util.Enumeration;


public class ProductDescriptor<T extends Object> {

	public static final class LongIDsGenerator implements Enumeration<Long> {

        public static final LongIDsGenerator Instance = new LongIDsGenerator();

        private Long next;

        private LongIDsGenerator(){ next = 0L;}

        public boolean seqAlreadyInitialized(){ return next > 0L; }

        //True => Initialized the sequence, False did nothing, seq already initialized
        public boolean setFirstInSequence(@NonNull Long first){
            if(first < 0L) throw new IllegalArgumentException("First may not be lower than 0L");
            if(seqAlreadyInitialized())
                return false;
            else{
                next = first; return true;
            }
        }

        @Override
        public Long nextElement() { return next++; }

        @Override
        public boolean hasMoreElements() { return true; }
    }

    public static Long nextLongID(){ return ProductDescriptor.LongIDsGenerator.Instance.nextElement(); }

    public final long id;
    public final String name;
    public final String category;
    public final String info;

    //https://en.wikipedia.org/wiki/Tag_(metadata)
    protected T tag = null;

    public T getTag() { return tag; }

    public void setTag(T tag) { this.tag = tag;}

    //http://www.javapractices.com/topic/TopicAction.do?Id=13
    protected BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NonNull BigDecimal price) throws IllegalArgumentException {

        if(price.compareTo(new BigDecimal(0)) < 0)
            throw new IllegalArgumentException("price < 0");

        this.price = price;
    }

    protected Double mUnity = 1D;

    public Double getUnity() {
        return mUnity;
    }

    public void setUnity(Double unity) {
        //TODO : validate parameter
        this.mUnity = unity;
    }

    public ProductDescriptor(
        long id,
        @NonNull String name,
        @NonNull String category,
        @NonNull String info,
        @NonNull BigDecimal price,
        @Nullable T tag
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.info = info;
        this.setPrice(price);
        this.setTag(tag);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(o instanceof ProductDescriptor) {
            ProductDescriptor oAsPD = (ProductDescriptor) o;
            return
                this.id == oAsPD.id
                &&
                this.name.equals(oAsPD.name)
                &&
                this.category.equals(oAsPD.category)
                &&
                this.price.compareTo(oAsPD.price) == 0;
        }
        else return false;
    }

    @Override
    public int hashCode(){
        //Making sure two equal product descriptor have the same hashcode
        //Two equal strings have the same hashcode
        //https://javaconceptoftheday.com/when-to-use-equals-hashcode-on-strings/
        return
            Long.valueOf(this.id).hashCode()
            ^
            this.name.hashCode()
        ;
        //The same Longs have the same hash
        //https://docs.oracle.com/javase/7/docs/api/java/lang/Long.html#hashCode()
    }
}
