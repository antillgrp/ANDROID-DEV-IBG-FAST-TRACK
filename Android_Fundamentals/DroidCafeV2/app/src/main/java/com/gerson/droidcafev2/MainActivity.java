package com.gerson.droidcafev2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //region DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //endregion

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_communicate) {
            Log.d("nav_communicate","onNavigationItemSelected");
        } else if (id == R.id.nav_camera) {
            Log.d("nav_camera","onNavigationItemSelected");
        } else if (id == R.id.nav_gallery) {
            Log.d("nav_gallery","onNavigationItemSelected");
        } else if (id == R.id.nav_slideshow) {
            Log.d("nav_slideshow","onNavigationItemSelected");
        } else if (id == R.id.nav_manage) {
            Log.d("nav_manage","onNavigationItemSelected");
        } else if (id == R.id.nav_share) {
            Log.d("nav_share","onNavigationItemSelected");
        } else if (id == R.id.nav_send) {
            Log.d("nav_send","onNavigationItemSelected");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public void launchOrderActivity(){
        startActivity(
                new Intent(MainActivity.this, OrderActivity.class)
        );
    }

    //region MENU CODE
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    protected void onMenuItemClick(@NonNull MenuItem item){
//        switch (item.getItemId()) {
//            case R.id.action_order:
//            case R.id.drawer_action_order:
//                displayToast(findViewById(R.id.fab),"Menu Order");
//                launchOrderActivity();
//                break;
//            case R.id.action_status:
//                displayToast(findViewById(R.id.fab),"Status requested");
//                break;
//            case R.id.action_favorites:
//                displayToast(findViewById(R.id.fab),"Show favorites");
//                break;
//            case R.id.action_contact:
//                displayToast(findViewById(R.id.fab),"Display contact info");
//                break;
//            default: break;
//        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        this.onMenuItemClick(item);

        return super.onOptionsItemSelected(item);
    }


    //endregion


}
