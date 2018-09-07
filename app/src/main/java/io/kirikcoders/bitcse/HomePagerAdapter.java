package io.kirikcoders.bitcse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

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
