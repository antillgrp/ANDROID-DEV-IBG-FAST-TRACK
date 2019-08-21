package com.gerson.droidcafedrawer.model;

import android.support.annotation.NonNull;

import java.math.BigDecimal;

public class MenuProductDescriptor extends ProductDescriptor {

    protected int imageResource = 0;

    public MenuProductDescriptor(
        long id,
        @NonNull String name,
        @NonNull String category,
        @NonNull String info,
        @NonNull BigDecimal price,
        int imageResource
    ) {
        super(id, name, category, info, price);
        this.imageResource = imageResource;
    }

    public MenuProductDescriptor(
            @NonNull ProductDescriptor pD,
            int imageResource
    ) {
        super(pD.id, pD.name, pD.category, pD.info, pD.price);
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
