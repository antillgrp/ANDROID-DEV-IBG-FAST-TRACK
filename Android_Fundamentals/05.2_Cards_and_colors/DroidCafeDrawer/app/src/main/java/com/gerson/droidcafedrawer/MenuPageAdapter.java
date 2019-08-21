package com.gerson.droidcafedrawer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gerson.droidcafedrawer.model.MenuProductDescriptor;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import android.support.v7.app.AlertDialog;

public class MenuPageAdapter extends FragmentStatePagerAdapter implements Serializable {

    public final HashMap<String, Collection<MenuProductDescriptor>> productsByCategory;

    public MenuPageAdapter(
            FragmentManager fm,
            @NonNull HashMap<String, Collection<MenuProductDescriptor>> productsByCategory
    ){
        super(fm);
        this.productsByCategory = productsByCategory;
    }

    //https://stackoverflow.com/a/509115/11082043
    //http://help.eclipse.org/kepler/index.jsp?topic=/org.eclipse.jdt.doc.user/tasks/task-suppress_warnings.htm
    @SuppressWarnings("All")
    public MenuPageAdapter(
            FragmentManager fm,
            Collection<MenuProductDescriptor> menuProducts
    ) {
        super(fm);

        this.productsByCategory = new HashMap<>();

        for ( MenuProductDescriptor mProd: menuProducts){

            Collection<MenuProductDescriptor> currentProdList;
            currentProdList = productsByCategory.getOrDefault(mProd.category, new LinkedList<>());

            productsByCategory.putIfAbsent(mProd.category, currentProdList);


            productsByCategory.get(mProd.category).add(mProd);
        }
    }


    protected String getCategory(int position) throws IndexOutOfBoundsException {

        if (position < 0 || position >= getCount()) throw new IndexOutOfBoundsException();

        return
            productsByCategory
            .keySet()
            .toArray(new String[0])
            [position];
    }

    @Override
    public int getCount() { return productsByCategory.size(); }

    @Override @NonNull
    public CharSequence getPageTitle(int position) throws IndexOutOfBoundsException {

        if (position < 0 || position >= getCount()) throw new IndexOutOfBoundsException();

        return this.getCategory(position);
    }


    @Override
    public Fragment getItem(int position) throws IndexOutOfBoundsException {

        if (position < 0 || position >= getCount()) throw new IndexOutOfBoundsException();

        Fragment fragment = new Fragment();

        if(this.getCount() > 0) {
            fragment = MenuPageFragment.newInstance(
                //CATEGORY PRODUCTS LIST COLLECTION
                (Serializable)
                Objects.requireNonNull(
                productsByCategory.get(this.getCategory(position))
                )
            );
        }

        return  fragment;
    }



    public static class
        MenuPageFragment
        extends
        Fragment {

        public static final String SERIALIZABLE_PROD_LIST = "SERIALIZABLE_PROD_LIST";

        public MenuPageFragment(){ super(); }

        public static MenuPageFragment newInstance(
            @NonNull Serializable menuProdList
        ) {
            MenuPageFragment fragment = new MenuPageFragment();
            Bundle args = new Bundle();
            args.putSerializable(SERIALIZABLE_PROD_LIST, menuProdList);
            fragment.setArguments(args);
            return fragment;
        }

        //https://stackoverflow.com/a/509115/11082043
        //http://help.eclipse.org/kepler/index.jsp?topic=/org.eclipse.jdt.doc.user/tasks/task-suppress_warnings.htm
        @SuppressWarnings("All")
        @Override
        public View onCreateView(
                @NonNull LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState
        ) {
            View rootView = inflater.inflate(R.layout.fragment_menu_page, container, false);

            if(this.getArguments() != null && this.getArguments().containsKey(SERIALIZABLE_PROD_LIST)){

                Serializable sprods = this.getArguments().getSerializable(SERIALIZABLE_PROD_LIST);

                //http://www.informit.com/articles/article.aspx?p=2861454&seqNum=2
                @SuppressWarnings("unchecked")
                List<MenuProductDescriptor> cprods = (List<MenuProductDescriptor>)sprods;

                RecyclerView mRecyclerView = rootView.findViewById(R.id.recyclerv_menu_entries);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                mRecyclerView.setAdapter(
                    new MenuProdsColl_RV_Adapter(
                        container.getContext(),
                        cprods != null ? cprods : new LinkedList<>()
                    )
                );

                ItemTouchHelper helper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(
                 ItemTouchHelper.UP | ItemTouchHelper.DOWN
                          |
                          ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                    ) {
                            @Override
                            public boolean onMove(
                                @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder,
                                @NonNull RecyclerView.ViewHolder target
                            ) {
                                int from = viewHolder.getAdapterPosition();
                                int to = target.getAdapterPosition();
                                Collections.swap(cprods, from, to);
                                mRecyclerView.getAdapter().notifyItemMoved(from, to);
                                return true;
                            }

                            @Override
                            public void onSwiped(
                                @NonNull RecyclerView.ViewHolder viewHolder,
                                int direction
                            ) {
                                cprods.remove(viewHolder.getAdapterPosition());
                                mRecyclerView
                                .getAdapter()
                                .notifyItemRemoved(
                            viewHolder.getAdapterPosition()
                                );
                            }
                        }
                );

                helper.attachToRecyclerView(mRecyclerView);
            }
            return rootView;
        }
    }

    public static class
        MenuProdsColl_RV_Adapter
        extends
        RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private final LayoutInflater layoutInflater;
        private final Collection<MenuProductDescriptor> prodsColl;

        public MenuProdsColl_RV_Adapter(
                @NonNull Context context,
                @NonNull Collection<MenuProductDescriptor> cprods
        ) {
            this.layoutInflater = LayoutInflater.from(context);
            this.prodsColl = cprods;
        }

        @Override
        public int getItemCount() {
            return this.prodsColl.size();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            return new RecyclerView.ViewHolder(
            this.layoutInflater.inflate(
                    R.layout.menu_entry_holder,
                    parent,
                    false
                )
            ){};
        }



        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            //https://stackoverflow.com/a/26308360/1043
            try {
                MenuProductDescriptor mProdD;
                mProdD = prodsColl.toArray(new MenuProductDescriptor[0])[position];

                TextView  textViewProdName = holder.itemView.findViewById(R.id.product_name);
                ImageView imageViewProdImg = holder.itemView.findViewById(R.id.product_img);
                TextView  textViewProdInfo = holder.itemView.findViewById(R.id.product_info);
                ImageButton imgButtonBuy = holder.itemView.findViewById(R.id.button_buy);

                textViewProdName.setText(mProdD.name);
                textViewProdInfo.setText(mProdD.info);
                Glide
                .with(holder.itemView.getContext())
                .load(mProdD.getImageResource())
                .into( imageViewProdImg );

                imageViewProdImg.setOnClickListener(
                    view -> {
                        //https://medium.com/@suragch/creating-a-custom-alertdialog-bae919d2efa5
                        // create an alert builder
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                holder.itemView.getContext()
                        );

                        //builder.setTitle(mProdD.name);

                        View card = this.layoutInflater.inflate(
                                R.layout.menu_entry_holder,
                                null
                        );

                        builder.setView(card);

                        TextView  l_textViewProdName = card.findViewById(R.id.product_name);
                        ImageView l_imageViewProdImg = card.findViewById(R.id.product_img);
                        TextView  l_textViewProdInfo = card.findViewById(R.id.product_info);
                        LinearLayout l_linearLayout = card.findViewById(R.id.toolbar);

                        l_textViewProdName.setText(mProdD.name);
                        l_textViewProdInfo.setText(mProdD.info);
                        Glide
                        .with(card.getContext())
                        .load(mProdD.getImageResource())
                        .into(imageViewProdImg);
                        l_linearLayout.setVisibility(LinearLayout.GONE);

                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                );

                holder.itemView.setTag(mProdD);
                imgButtonBuy.setTag(mProdD);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

