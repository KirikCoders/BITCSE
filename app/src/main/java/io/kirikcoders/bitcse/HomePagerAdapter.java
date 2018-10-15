package io.kirikcoders.bitcse;


import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>(5);

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = fragmentArrayList.get(position);
        return f;
    }

    @Override
    public int getCount() {
        return 5;
    }

    public void addFragment(Fragment f){
        fragmentArrayList.add(f);
    }
    public Fragment getFragment(int position){
        return fragmentArrayList.get(position);
    }
}
