package alexanderivanets.cloudyapp.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import alexanderivanets.cloudyapp.R;
import alexanderivanets.cloudyapp.adapter.LocationsAdapter;
import alexanderivanets.cloudyapp.model.Config;
import alexanderivanets.cloudyapp.presenter.LocationsActivityP;
import alexanderivanets.cloudyapp.presenter.LocationsActivityPImpl;
import alexanderivanets.cloudyapp.utils.database.DBHandle;
import alexanderivanets.cloudyapp.utils.database.DBQueries;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationsActivity extends AppCompatActivity  implements LocationsActivityV{

    @BindView(R.id.vp_locations) ViewPager viewPager;
    @BindView(R.id.tl_locations) TabLayout tabLayout;
    @BindView(R.id.card_view_locations) CardView cardView;
    private PlaceAutocompleteFragment autocompleteFragment;
    private boolean isClicked = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        ButterKnife.bind(this);

        autocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                DBQueries.addToDatabase(place,getApplicationContext(), DBHandle.TABLE_NAME_RECENT);
                Intent intent = new Intent(LocationsActivity.this, MainActivity.class);
                intent.putExtra(Config.ITEM_FROM_LOCATION_ACTIVITY,true);
                intent.putExtra(Config.ITEM_NUMBER,-1);
                intent.putExtra(DBHandle.CITY_NAME_TXT,place.getName().toString());
                intent.putExtra(DBHandle.LAT_TXT, place.getLatLng().latitude);
                intent.putExtra(DBHandle.LON_TXT, place.getLatLng().longitude);
                startActivity(intent);
                isClicked = false;
            }

            @Override
            public void onError(Status status) {
                isClicked = false;
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClicked = !isClicked;
                cardView.setClickable(!isClicked);
            }
        });


        setupViewPager();


    }


    @Override
    public void onBackPressed() {
    }

    private void setupViewPager(){
        LocationsAdapter adapter = new LocationsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }




}
