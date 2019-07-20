package com.gerson.droidcafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //region STATICS
    public static void displayToast(View view, String message) {
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            launchOrderActivity();
//          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//          .setAction("Action", null).show();
            }
        });
    }

    public void launchOrderActivity(){
        startActivity(
                new Intent(MainActivity.this, OrderActivity.class)
        );
    }

    /**
     * Shows a message that the donut image was clicked.
     */
    public void showDonutOrder(View view) {
        displayToast(view, getString(R.string.donut_order_message));
    }

    /**
     * Shows a message that the ice cream sandwich image was clicked.
     */
    public void showIceCreamOrder(View view) {
        displayToast(view, getString(R.string.ice_cream_order_message));
    }

    /**
     * Shows a message that the froyo image was clicked.
     */
    public void showFroyoOrder(View view) {
        displayToast(view, getString(R.string.froyo_order_message));
    }

    public void openOrderActivity(View view) {
        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_order:
                displayToast(findViewById(R.id.fab),"Menu Order");
                launchOrderActivity();
                break;
            case R.id.action_status:
                displayToast(findViewById(R.id.fab),"Status requested");
                break;
            case R.id.action_favorites:
                displayToast(findViewById(R.id.fab),"Show favorites");
                break;
            case R.id.action_contact:
                displayToast(findViewById(R.id.fab),"Display contact info");
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }
}
