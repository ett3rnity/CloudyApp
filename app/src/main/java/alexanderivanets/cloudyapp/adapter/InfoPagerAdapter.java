package alexanderivanets.cloudyapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import alexanderivanets.cloudyapp.view.DetailedInfoFragment;

/**
 * Created by root on 20.08.17.
 */

public class InfoPagerAdapter extends FragmentStatePagerAdapter {
    private String[] tabs = {"HOURS","DAYS"};
    private double mLat;
    private double mLon;

    public InfoPagerAdapter(FragmentManager fm, double mLat, double mLon) {
        super(fm);
        this.mLat = mLat;
        this.mLon = mLon;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailedInfoFragment.newInstance(position+1, mLat, mLon);
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
