package com.gerson.droidcafedrawer;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ResToFragAdapter extends Fragment {

    //region STATICS
    public final static String RESOURCE = "RESOURCE";


    @NonNull
    //@org.jetbrains.annotations.Contract("_, _ -> new")
    public static FragmentStatePagerAdapter ResArrToPagAdapter(
            final FragmentManager fm,
            final @NonNull @LayoutRes int [] resources
    ){
        return new FragmentStatePagerAdapter(fm) {

            @Override
            //@org.jetbrains.annotations.Contract(pure = true)
            public int getCount() {
                return  resources.length;
            }

            @Override
            public Fragment getItem(int position) {
                ResToFragAdapter result = new ResToFragAdapter();
                if(this.getCount() > 0){
                    Bundle configBundle = new Bundle();
                    configBundle.putInt( RESOURCE, resources[position]);
                    result.setArguments(configBundle);
                }
                return  result;
            }
        };
    }
    //endregion

    public ResToFragAdapter(){
        Log.d("::ResToFragAdapter::","Constructor");
    }

    //Not Necessary
    //public void onSaveInstanceState(Bundle outState)

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        if(this.getArguments() != null && this.getArguments().containsKey(RESOURCE)){
            return inflater.inflate(
                    this.getArguments().getInt(RESOURCE),
                    container,
                    false
            );
        }
        return new View(this.getContext());
    }
}