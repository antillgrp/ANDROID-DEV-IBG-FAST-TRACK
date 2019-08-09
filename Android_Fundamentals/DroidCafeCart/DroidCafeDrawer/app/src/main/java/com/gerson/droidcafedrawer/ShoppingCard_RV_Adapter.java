package com.gerson.droidcafedrawer;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gerson.droidcafedrawer.model.*;
import android.support.annotation.NonNull;

import java.util.Map;

public  class
            ShoppingCard_RV_Adapter
        extends
            RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    protected final ShoppingCart shoppingCart;
    private final LayoutInflater layoutInflater;

    public ShoppingCard_RV_Adapter(@NonNull Context context, @NonNull ShoppingCart shoppingCart) {
        this.layoutInflater = LayoutInflater.from(context);
        this.shoppingCart = shoppingCart;
    }

    @Override
    public int getItemCount() { return this.shoppingCart.size(); }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new RecyclerView.ViewHolder(
                this.layoutInflater.inflate(
                    R.layout.cart_prod_holder,
                    parent,
                    false
                )
        ){
            public final TextView textViewProdName = itemView.findViewById(R.id.product_name);
            public final TextView textViewProdAmount = itemView.findViewById(R.id.product_amount);
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //https://stackoverflow.com/a/26308360/11082043
        try {

            // Retrieve the data for that position.
            Map.Entry<ProductDescriptor,Double> entry = (Map.Entry)shoppingCart.entrySet().toArray()[position];

            ((TextView)holder.getClass().getField("textViewProdName").get(holder))
            .setText(entry.getKey().name);

            ((TextView)holder.getClass().getField("textViewProdAmount").get(holder))
            .setText(String.valueOf(entry.getValue()));

            holder.itemView.setTag(entry.getKey());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
