package com.gerson.droidcafeadaptative.ui.ui_00_home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import com.gerson.droidcafeadaptative.model.VendingMachineViewModel;

public class
        MenuPagerRvAdapter
    extends
        FragmentStatePagerAdapter {

    private final Fragment parentFragment;

    public MenuPagerRvAdapter(
        @NonNull FragmentManager fm,
        @NonNull Fragment parentFragment
    ) {
        //https://stackoverflow.com/a/56208500
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.parentFragment = parentFragment; // viewmodels owner
    }

    @Override
    public int getCount() {
        return
            ViewModelProviders
            .of(this.parentFragment)
            .get(VendingMachineViewModel.class)
			.getVendingMachine()
			.getProductByCategoryMapCollection()
            .size();
    }

    @Override @NonNull
    public CharSequence getPageTitle(int position) throws IndexOutOfBoundsException
    {
        if (position < 0 || position >= getCount())
            throw new IndexOutOfBoundsException(/*TODO*/);

        return this.getCategory(position);
    }

    private String getCategory(int position) {
        return
            ViewModelProviders
            .of(this.parentFragment)
            .get(VendingMachineViewModel.class)
            .getVendingMachine()
			.getProductByCategoryMapCollection()
			.keySet()
			.toArray(new String[1])
			[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) throws IndexOutOfBoundsException
    {
        if (position < 0 || position >= getCount())
            throw new IndexOutOfBoundsException(/*TODO*/);
        return
            this.getCount() <= 0
            ?
            new Fragment() // blank filler fragment
            :
            MenuPageFragment.newInstance( this.getCategory(position) );
    }
}

