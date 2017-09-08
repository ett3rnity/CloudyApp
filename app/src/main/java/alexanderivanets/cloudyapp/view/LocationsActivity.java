package alexanderivanets.cloudyapp.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import alexanderivanets.cloudyapp.R;
import alexanderivanets.cloudyapp.adapter.LocationsAdapter;
import alexanderivanets.cloudyapp.presenter.LocationsActivityP;
import alexanderivanets.cloudyapp.presenter.LocationsActivityPImpl;
import alexanderivanets.cloudyapp.utils.database.DBHandle;
import alexanderivanets.cloudyapp.utils.database.DBQueries;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsActivity extends AppCompatActivity  implements LocationsActivityV{

    @BindView(R.id.vp_locations) ViewPager viewPager;
    @BindView(R.id.tl_locations) TabLayout tabLayout;
    PlaceAutocompleteFragment autocompleteFragment;

    private LocationsActivityP presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        ButterKnife.bind(this);

        presenter = new LocationsActivityPImpl(this, getApplicationContext());

        autocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // FIXME: 06.09.17 HERE WE GONA START MAIN ACTIVIVITY FROM INTENT
                // PASS COORDINATES TO ACTIVITY,FROM ACTIVITY TO FRAGMENTS,TOO
                //AND SAVE INFO INTO DATABASE
                DBQueries.addToDatabase(place,getApplicationContext(), DBHandle.TABLE_NAME_RECENT);
            }

            @Override
            public void onError(Status status) {

            }
        });


        setupViewPager();


    }


    private void setupViewPager(){
        LocationsAdapter adapter = new LocationsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }



    @Override
    public void showRecent() {

    }

    @Override
    public void ShowFavourites() {

    }



}
