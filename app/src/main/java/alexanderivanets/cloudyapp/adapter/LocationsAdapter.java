package alexanderivanets.cloudyapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import alexanderivanets.cloudyapp.view.LocationsFragment;

/**
 * Created by root on 01.09.17.
 */

public class LocationsAdapter extends FragmentStatePagerAdapter {

    private String[] tabName = {"RECENT", "FAVOURITES"};


    public LocationsAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getCount() {
        return tabName.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabName[position];
    }

    @Override
    public Fragment getItem(int position) {
        return LocationsFragment.newInstance(position);
    }
}
