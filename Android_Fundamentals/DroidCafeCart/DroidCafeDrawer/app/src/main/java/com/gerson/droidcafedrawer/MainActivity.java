package com.gerson.droidcafedrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;
import com.gerson.droidcafedrawer.model.ProductDescriptor;
import com.gerson.droidcafedrawer.model.ShoppingCart;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //region STATICS
    public static final String RESOURCES_BUNDLE_KEY = "com.gerson.droidcafe.RESOURCES";

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

    private void launchOrderActivity(){
        startActivity(
                new Intent(MainActivity.this, OrderActivity.class)
        );
    }

    public void onAnyMenuItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_order:
                displayToast(findViewById(R.id.fab), "Menu Order");
                launchOrderActivity();
                break;
            case R.id.action_status:
                displayToast(findViewById(R.id.fab), "Status requested");
                break;
            case R.id.action_favorites:
                displayToast(findViewById(R.id.fab), "Show favorites");
                break;
            case R.id.action_contact:
                displayToast(findViewById(R.id.fab), "Display contact info");
                break;
            default:
                Log.d("NOT_KNOWN_MENU_ITEM", "Menu Item Id:" + String.valueOf(item.getItemId()));
                break;
        }
    }

    private ShoppingCart shoppingCart = new ShoppingCart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //region TabLayout
        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText("Hamburgers"));
        tabLayout.addTab(tabLayout.newTab().setText("Desserts"));
        tabLayout.addTab(tabLayout.newTab().setText("Drinks"));
        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Defining tab fragments content as resources
        final ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(
                ResToFragAdapter.ResArrToPagAdapter(
                        this.getSupportFragmentManager(),
                        new int []{
                                R.layout.tab_frag_burgers,
                                R.layout.tab_frag_desserts,
                                R.layout.tab_frag_drinks
                        }
                )
        );
        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout)
        );
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
        //endregion

        //region DRAWER/NAVIGATION
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //https://stackoverflow.com/a/46185984/11082043
        navigationView.bringToFront();
        //endregion

        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(new ShoppingCard_RV_Adapter(this, shoppingCart));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //++
    public void increaseCartProdAmount(@NonNull View view) { this.alterCartProdAmount(view,1.0d); }

    //--
    public void decreaseCartProdAmount(@NonNull View view) { this.alterCartProdAmount(view,-1.0d); }

    public void alterCartProdAmount(@NonNull View view, Double newAmount) {

        int prevSize =  shoppingCart.size();

        switch (view.getId()) {
            case R.id.button_add:
            case R.id.button_sub: {
                shoppingCart.put(
                        (ProductDescriptor)((View)view.getParent()).getTag(),
                        newAmount
                );
                break;
            }
            case R.id.cuban_burger: {
                shoppingCart.put(
                        new ProductDescriptor(
                                1,
                                "Cuban Burger",
                                new BigDecimal(7)
                        ),
                        newAmount
                );
                break;
            }
            case R.id.donut: {
                shoppingCart.put(
                    new ProductDescriptor(
                        2,
                        "Donut",
                        new BigDecimal(2)
                    ),
                    newAmount
                );
                break;
            }
            case R.id.milk_shake: {
                shoppingCart.put(
                        new ProductDescriptor(
                                3,
                                "Milk Shake",
                                new BigDecimal(3)
                        ),
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
        weight =    shoppingCart.size() > prevSize //if added
                    ?
                    weight + deltha < 0.5f ? weight + deltha : weight
                    :
                    shoppingCart.size() < prevSize //if removed
                    ?
                    weight - deltha >= 0 ? weight -deltha : weight
                    :
                    weight
        ;
        ((LinearLayout.LayoutParams)mRecyclerView.getLayoutParams()).weight = weight;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
