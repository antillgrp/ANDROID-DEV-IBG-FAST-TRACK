package com.gerson.lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

    private void logCalledMethod(String methodName){
        Log.d(
                MainActivity.class.getSimpleName(),
                MessageFormat.format(
                        "{0}:CALLED:{1}",
                        java.text.DateFormat.getDateTimeInstance().format(java.util.Calendar.getInstance().getTime()),
                        methodName
                )
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.logCalledMethod("onCreate");

        setContentView(R.layout.activity_main);

        //https://codelabs.developers.google.com/codelabs/android-training-activity-lifecycle-and-state/index.html?index=..%2F..android-training#3
        if (savedInstanceState != null && savedInstanceState.containsKey("TextViewIsVisible")) {
            ((TextView)this.findViewById(R.id.textView)).setVisibility(
                savedInstanceState.getBoolean("TextViewIsVisible")
                ?
                View.VISIBLE
                :
                View.INVISIBLE
            );
        }
    }

    public void show_hide_TextView(View view) {
        ((TextView)this.findViewById(R.id.textView)).setVisibility(
                ((android.widget.Switch)view).isChecked() ? View.INVISIBLE : View.VISIBLE
        );
    }

    //https://codelabs.developers.google.com/codelabs/android-training-activity-lifecycle-and-state/index.html?index=..%2F..android-training#3
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(
                "TextViewIsVisible",
                ((TextView)this.findViewById(R.id.textView)).getVisibility() == View.VISIBLE
        );
    }

    //https://codelabs.developers.google.com/codelabs/android-training-activity-lifecycle-and-state/index.html?index=..%2F..android-training#0

    @Override public void onStart(){ super.onStart(); this.logCalledMethod("onStart"); }

    @Override public void onResume(){ super.onResume(); this.logCalledMethod("onResume"); }

    @Override public void onPause(){ super.onPause(); this.logCalledMethod("onPause"); }

    @Override public void onStop(){ super.onStop(); this.logCalledMethod("onStop"); }

    @Override public void onRestart(){ super.onRestart(); this.logCalledMethod("onRestart"); }

    @Override public void onDestroy(){ super.onDestroy(); this.logCalledMethod("onDestroy"); }
}
