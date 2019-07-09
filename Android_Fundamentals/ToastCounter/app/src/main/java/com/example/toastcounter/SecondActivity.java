package com.example.toastcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Enumeration;

public class SecondActivity extends AppCompatActivity {

    /**
     * USAGE:
     *
     * Ej.
     *
     * new CircularStringsEnum ( new String[] { "Hello World!", "Hola Mundo!",  "Hallo Welt!", "Bonjour le monde!", "你好，世界!"} )
     *
     * */
    private class CircularStringsEnum implements Enumeration<String>{

        private String[] strings; //empty
        private int current = 0;

        public CircularStringsEnum(String[] strings){
            java.util.Objects.requireNonNull(strings);
            this.strings = strings;
        }

        @Override
        public boolean hasMoreElements() {
            return this.strings.length > 0;
        }

        @Override
        public String nextElement() {
            return strings[ current == strings.length ? (current = 1) * 0 : current ++];
        }
    }

    private Enumeration<String> ToastStrings = new CircularStringsEnum ( new String[] { "Hello World!", "Hola Mundo!",  "Hallo Welt!", "Bonjour le monde!", "你好，世界!"} );

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showToast(View view) {
        Toast.makeText(
                this,
                this.ToastStrings.hasMoreElements() ? this.ToastStrings.nextElement() : "No Toast",
                Toast.LENGTH_SHORT
        ).show();
    }

    public void countUp(View view) {
        ((TextView) findViewById(R.id.show_count)).setText(Integer.toString(++this.counter));
    }
}
