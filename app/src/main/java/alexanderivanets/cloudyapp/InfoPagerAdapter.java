package alexanderivanets.cloudyapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by root on 20.08.17.
 */

public class InfoPagerAdapter extends FragmentStatePagerAdapter {
    private String[] tabs = {"HOURS","DAYS"};

    public InfoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DetailedInfoFragment.newInstance();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
