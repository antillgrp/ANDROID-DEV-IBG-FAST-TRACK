package com.gerson.droidcafedrawer;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gerson.droidcafedrawer.model.MenuProductDescriptor;
import com.gerson.droidcafedrawer.model.ProductDescriptor;
import com.gerson.droidcafedrawer.model.ShoppingCart;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class
        MainActivity
    extends
        AppCompatActivity
    implements
        NavigationView.OnNavigationItemSelectedListener {

    //region STATICS
    public static void displayToast(@NonNull View view, String message) {
        Toast
                .makeText(
                        view.getContext(),
                        message,
                        Toast.LENGTH_SHORT
                )
                .show();

        Snackbar
                .make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    //endregion

    protected void launchOrderActivity(){
        startActivity(
                new Intent(MainActivity.this, OrderActivity.class)
        );
    }

    public void onAnyMenuItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.day_night_mode_switch: {
                displayToast(findViewById(R.id.spacer), "day_night_mode_switch");
                // Get the night mode state of the app.
                int nightMode = AppCompatDelegate.getDefaultNightMode();
                //Set the theme mode for the restarted activity
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate
                    .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                else{
                    AppCompatDelegate
                    .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
                break;
            }
            case R.id.reload_prod_menu: {
                displayToast(findViewById(R.id.spacer), "reload_prod_menu");
                SetUpTabLayout(null);
                break;
            }
            case R.id.action_order:
                displayToast(findViewById(R.id.spacer), "Menu Order");
                //launchOrderActivity();
                break;
            case R.id.action_status:
                displayToast(findViewById(R.id.spacer), "Status requested");
                break;
            case R.id.action_favorites:
                displayToast(findViewById(R.id.spacer), "Show favorites");
                break;
            case R.id.action_contact:
                displayToast(findViewById(R.id.spacer), "Display contact info");
                break;
            default:
                Log.d("NOT_KNOWN_MENU_ITEM", "Menu Item Id:" + String.valueOf(item.getItemId()));
                break;
        }
    }

    //region DRAWER/NAVIGATION

    protected void SetUpDrawer(Toolbar toolbar){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(
                    this,
                drawer,
                    toolbar,
    R.string.navigation_drawer_open,
    R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //https://stackoverflow.com/a/46185984/11082043
        navigationView.bringToFront();
    }

    //endregion

    //region Food Menu Creation/Manipulation

    LinkedList<MenuProductDescriptor> foodMenu = new LinkedList<>();

    private Long nextLongID(){ return ProductDescriptor.LongIDsGenerator.Instance.nextElement(); }

    private int nameToImgRes(String imgName) {
        return  getResources().getIdentifier(
                imgName,
                "drawable",
                "com.gerson.droidcafedrawer"
        );
    }

    protected LinkedList<MenuProductDescriptor> getFoodMenu(){

        LinkedList<MenuProductDescriptor> lFoodMenu = new LinkedList<>();

        lFoodMenu.add(
                new MenuProductDescriptor(
                        nextLongID(),
                        "Cuban Burger",
                        "Burger",
                        "Cuban Sandwich Burger. " +
                                "A simply seasoned ground pork patty combined with deli ham, " +
                                "Swiss cheese and mustard on a crusty burger bun.",
                        new BigDecimal("5"),
                        nameToImgRes("img_cuban_burger")
                )
        );
        lFoodMenu.add(
                new MenuProductDescriptor(
                        nextLongID(),
                        "Sriracha Burger",
                        "Burger",
                        "A Peanut Butter Sriracha Bacon Cheeseburger combines sweet, " +
                                "salty smoky and spicy flavours into a taste explosion of a burger.",
                        new BigDecimal("5"),
                        nameToImgRes("img_sriracha_burger")
                )
        );
        lFoodMenu.add(
                new MenuProductDescriptor(
                        nextLongID(),
                        "Barbecue Burger",
                        "Burger",
                        "Barbecue Bacon Cheddar Sliders. A terrific " +
                                "way to feed the crowd at parties, cookouts, while tailgating " +
                                "or just for a casual pot luck dinner.",
                        new BigDecimal("5"),
                        nameToImgRes("img_barbecue_burger")
                )
        );
        lFoodMenu.add(
                new MenuProductDescriptor(
                        nextLongID(),
                        "Donut",
                        "Dessert",
                        "Donuts are glazed and sprinkled with candy.",
                        new BigDecimal("5"),
                        nameToImgRes("img_donut_circle")
                )
        );
        lFoodMenu.add(
                new MenuProductDescriptor(
                        nextLongID(),
                        "Ice Cream Sandwich",
                        "Dessert",
                        "Ice cream sandwiches have chocolate wafers and vanilla filling.",
                        new BigDecimal("5"),
                        nameToImgRes("img_icecream_circle")
                )
        );
        lFoodMenu.add(
                new MenuProductDescriptor(
                        nextLongID(),
                        "Froyo",
                        "Dessert",
                        "FroYo is premium self-serve frozen yogurt.",
                        new BigDecimal("5"),
                        nameToImgRes("img_froyo_circle")
                )
        );
        lFoodMenu.add(
                new MenuProductDescriptor(
                        nextLongID(),
                        "Milk Shake",
                        "Drink",
                        "Made with ice cream, milk, and any additional flavors, " +
                                "it’s a well-loved companion to a burger and fries " +
                                "or simply enjoyed alone as sweet treat.",
                        new BigDecimal("5"),
                        nameToImgRes("img_milk_shake")
                )
        );
        lFoodMenu.add(
                new MenuProductDescriptor(
                        nextLongID(),
                        "Natural Juice",
                        "Drink",
                        "Juice is a drink made from the extraction or pressing of the " +
                                "natural liquid contained in fruit and vegetables.",
                        new BigDecimal("5"),
                        nameToImgRes("img_nat_juice")
                )
        );
        lFoodMenu.add(
                new MenuProductDescriptor(
                        nextLongID(),
                        "Fountain Soda",
                        "Drink",
                        "Your favorite ice cold soda anytime, at the push of a button, " +
                                "carbonated flavors, and 1 non-carbonated, " +
                                "plus you get Ice cold Soda Water, and Ice.",
                        new BigDecimal("5"),
                        nameToImgRes("img_fountain_soda")
                )
        );

        return lFoodMenu;
    }

    protected void SetUpTabLayout(
            @Nullable HashMap<String, Collection<MenuProductDescriptor>> productsByCategory
    ){
        //Defining tab fragments content as resources
        final ViewPager viewPager = findViewById(R.id.pager);

        PagerAdapter pagerAdapter = productsByCategory != null
                ?
                new MenuPageAdapter(
                        this.getSupportFragmentManager(),
                        productsByCategory
                )
                :
                new MenuPageAdapter(
                        this.getSupportFragmentManager(),
                        getFoodMenu()
                )
                ;
        viewPager.setAdapter( pagerAdapter );

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.removeAllTabs();
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabLayout.addTab(
                    tabLayout.newTab().setText(
                            pagerAdapter.getPageTitle(i)
                    )
            );
        }

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Setting a listener for clicks.
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout)
        );

        tabLayout.clearOnTabSelectedListeners();
        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {}
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {}
                }
        );
    }

    //endregion

    //region ShoppingCart Creation/Manipulation

    private ShoppingCart shoppingCart = new ShoppingCart();
    //++
    public void increaseCartProdAmount(@NonNull View view) {
        this.alterCartProdAmount(view,1.0d);
    }
    //--
    public void decreaseCartProdAmount(@NonNull View view) {
        this.alterCartProdAmount(view,-1.0d);
    }

    protected void alterCartProdAmount(@NonNull View view, Double newAmount) {

        ShoppingCart cart = this.shoppingCart;

        int prevSize =  cart.size();

        switch (view.getId()) {
            case R.id.button_buy:
            case R.id.button_add:
            case R.id.button_sub: {
                cart.put(
                        //SEE: ShoppingCart_RV_Adapter:LINE:57, MenuPageAdapter:LINE:278
                        (ProductDescriptor)view.getTag(),
                        newAmount
                );
                break;
            }
            default:
                Log.d(":NOT_KNOWN_PRODUCT_IMG:", "Menu Item Id:" + view.getId());
                return;
        }

        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.getAdapter().notifyDataSetChanged();

        //https://stackoverflow.com/a/4641117/11082043
        float weight = ((LinearLayout.LayoutParams)mRecyclerView.getLayoutParams()).weight;
        float deltha = 0.10f;
        weight =    cart.size() > prevSize //if added
                ?
                weight + deltha < 0.5f ? weight + deltha : weight
                :
                cart.size() < prevSize //if removed
                ?
                weight - deltha >= 0 ? weight -deltha : weight
                :
                weight
        ;
        ((LinearLayout.LayoutParams)mRecyclerView.getLayoutParams()).weight = weight;
    }

    protected void SetUpSCardRecyclerView(@NonNull ShoppingCart shoppingCart, float weight){
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(new ShoppingCart_RV_Adapter(this, shoppingCart));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((LinearLayout.LayoutParams)mRecyclerView.getLayoutParams()).weight = weight;
    }

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        this.SetUpDrawer(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        HashMap<String, Collection<MenuProductDescriptor>> productsByCategory = null;

        float weight = 0f;

        if(savedInstanceState != null){

            this.shoppingCart = (ShoppingCart)savedInstanceState.getSerializable("SHOPPINGCART");

            weight = savedInstanceState.getFloat("RECYCLEVIEW_WEIGHT");

            //https://stackoverflow.com/a/509115/11082043
            /*
                (*)A workaround for the annotation needing to accompany the local variable declaration,
                which may be in a different scope on a different line than the actual cast, is to
                create a local variable within the scope of the cast specifically to perform the
                cast on the same line as the declaration. Then assign this variable to the actual
                variable which is in a different scope. This is the method I used also to suppress
                the warning on a cast to an instance variable as the annotation can't be applied
                here either.
             */

            @SuppressWarnings("unchecked")
            HashMap<String, Collection<MenuProductDescriptor>> s //read (*)
            =
            productsByCategory
            =
            (HashMap<String, Collection<MenuProductDescriptor>>)
            savedInstanceState.getSerializable("PRODUCTS_BY_CATEGORY");
        }

        this.SetUpTabLayout(productsByCategory);

        this.SetUpSCardRecyclerView(this.shoppingCart, weight);
    }

    //https://codelabs.developers.google.com/codelabs/android-training-activity-lifecycle-and-state/index.html?index=..%2F..android-training#3
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(
            "SHOPPINGCART",
            this.shoppingCart
        );
        outState.putFloat(
            "RECYCLEVIEW_WEIGHT",
            ((LinearLayout.LayoutParams)findViewById(R.id.recyclerview).getLayoutParams()).weight
        );
        outState.putSerializable(
            "PRODUCTS_BY_CATEGORY",
            (
                (MenuPageAdapter)
                Objects.requireNonNull(
                ((ViewPager) findViewById(R.id.pager)).getAdapter()
                )
            )
            .productsByCategory
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        this.onAnyMenuItemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        this.onAnyMenuItemSelected(item);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
