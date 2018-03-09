package com.group18.cs446.spacequest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int nrTabs;

    PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.nrTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new SectorItems();
            case 1:
                return new SectorItems();
            case 2:
                return new SectorItems();
            case 3:
                return new SectorItems();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nrTabs;
    }
}
