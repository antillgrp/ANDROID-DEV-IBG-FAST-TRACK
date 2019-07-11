package com.gerson.msgexchange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.gerson.twoactivities.extra.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ((TextView)findViewById(R.id.text_message))
        .setText(
            this.getIntent().hasExtra(MainActivity.EXTRA_MESSAGE)
            ?
            this.getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE)
            :
            ""
        );
    }

    public void second_button_send_onClick(View view) {

        Log.d(SecondActivity.class.getSimpleName(), "SECOND send_button clicked!");

        this.getIntent().putExtra(EXTRA_REPLY , ((EditText)findViewById(R.id.editText_main)).getText().toString());

        this.setResult(RESULT_OK,this.getIntent());

        this.finish();
    }
}
