package com.gerson.droidcafedrawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.gerson.droidcafedrawer.model.*;
import android.support.annotation.NonNull;
import java.util.Map;

public  class
ShoppingCart_RV_Adapter
        extends
            RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    protected final ShoppingCart shoppingCart;
    private final LayoutInflater layoutInflater;

    public ShoppingCart_RV_Adapter(@NonNull Context context, @NonNull ShoppingCart shoppingCart) {
        this.layoutInflater = LayoutInflater.from(context);
        this.shoppingCart = shoppingCart;
    }

    @Override
    public int getItemCount() { return this.shoppingCart.size(); }

    @Override @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new RecyclerView.ViewHolder(
                this.layoutInflater.inflate(
                    R.layout.cart_prod_holder,
                    parent,
                    false
                )
        ){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(position < 0 || position >= shoppingCart.size())
            throw new IndexOutOfBoundsException("ShoppingCart_RV_Adapter::onBindViewHolder -> position");

        try {
            // Retrieve the data for that position.

            //https://stackoverflow.com/a/509115/11082043
            //http://help.eclipse.org/kepler/index.jsp?topic=/org.eclipse.jdt.doc.user/tasks/task-suppress_warnings.htm
            @SuppressWarnings("All")
            Map.Entry<ProductDescriptor,Double> entry = (Map.Entry<ProductDescriptor,Double>)shoppingCart.entrySet().toArray()[position];

            TextView textViewProdName = holder.itemView.findViewById(R.id.product_name);
            TextView textViewProdAmount = holder.itemView.findViewById(R.id.product_amount);
            ImageButton imgButtonAdd = holder.itemView.findViewById(R.id.button_add);
            ImageButton imgButtonSub = holder.itemView.findViewById(R.id.button_sub);

            textViewProdName.setText(entry.getKey().name);
            textViewProdAmount.setText(String.valueOf(entry.getValue()));
            imgButtonAdd.setTag(entry.getKey());
            imgButtonSub.setTag(entry.getKey());
            //holder.itemView.setTag(entry.getKey());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}