package com.example.mainactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {


    private class InfiniteGreetingGenerator implements Iterable<String>{

        private String[] greetings = new String[] { "Hello World!", "Hola Mundo!",  "Hallo Welt!", "Bonjour le monde!", "你好，世界!"};
        private int current = 0;

        @Override
        public Iterator<String> iterator(){
            return new Iterator<String>() {
                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public String next() {
                    return greetings[ current == greetings.length ? (current = 1) * 0 : current ++];
                }
                @Override
                public void remove() {
                }
            };
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Iterator Greeter = (new InfiniteGreetingGenerator()).iterator();

        final TextView greeting = (TextView) findViewById(R.id.greeting);
        final Button next = (Button) findViewById(R.id.next);

        View.OnClickListener ocl = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                greeting.setText(Greeter.next().toString());
            }
        };
        next.setOnClickListener(ocl);
    }
}
