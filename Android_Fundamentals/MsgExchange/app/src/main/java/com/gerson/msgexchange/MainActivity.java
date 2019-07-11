package com.gerson.msgexchange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import android.util.Log;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.gerson.twoactivities.extra.MESSAGE";

    public static final int TEXT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void first_button_send_onClick(View view) {

        Log.d(MainActivity.class.getSimpleName(), "FIRST send_button clicked!");

        Intent intent = new Intent(this, SecondActivity.class);

        intent.putExtra(EXTRA_MESSAGE, ((EditText)findViewById(R.id.editText_main)).getText().toString());

        this.startActivityForResult(intent, TEXT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {

        super.onActivityResult(requestCode, resultCode, dataIntent);

        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK) {

            ((EditText)findViewById(R.id.editText_main)).setText("");
            
            ((TextView)findViewById(R.id.text_message))
            .setText(
                dataIntent.hasExtra(SecondActivity.EXTRA_REPLY)
                ?
                dataIntent.getStringExtra(SecondActivity.EXTRA_REPLY)
                :
                ""
            );

        }
    }


}