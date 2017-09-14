package alexanderivanets.cloudyapp.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import alexanderivanets.cloudyapp.adapter.InfoPagerAdapter;
import alexanderivanets.cloudyapp.R;
import alexanderivanets.cloudyapp.adapter.ThemeAdapter;
import alexanderivanets.cloudyapp.model.Config;
import alexanderivanets.cloudyapp.model.ItemFromDatabase;
import alexanderivanets.cloudyapp.model.fivedayresponse.FiveDayResponse;
import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;
import alexanderivanets.cloudyapp.presenter.MainActivityP;
import alexanderivanets.cloudyapp.presenter.MainActivityPImpl;
import alexanderivanets.cloudyapp.utils.database.DBHandle;
import alexanderivanets.cloudyapp.utils.database.DBQueries;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityV {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;

    private MainActivityP presenter;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private Handler handler;

    private Bundle bundle;


    private boolean anotherActivity;



    @BindView(R.id.tv_main_cityName)
    TextView cityName;
    @BindView(R.id.tv_main_date)
    TextView date;
    @BindView(R.id.tv_main_tempNowNumb)
    TextView tempNow;
    @BindView(R.id.tv_main_weatherNowDescr)
    TextView weatherNow;
    @BindView(R.id.tv_main_windNow)
    TextView windNow;
    @BindView(R.id.tv_main_sunrise)
    TextView sunRise;
    @BindView(R.id.tv_main_sunset)
    TextView sunSet;
    @BindView(R.id.iv_main_pic)
    ImageView mainPic;
    @BindView(R.id.vp_main)
    ViewPager viewPager;
    @BindView(R.id.tl_main)
    TabLayout tabLayout;
    @BindView(R.id.btn_main_locations)
    ImageView goToLocations;
    @BindView(R.id.pb_main)
    ProgressBar progressBar;
    @BindView(R.id.card_view_main)
    CardView slider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        handler = new Handler();
        setUpBroadcastReceiving();
        this.registerReceiver(broadcastReceiver, intentFilter);

        presenter = new MainActivityPImpl(this);


        bundle = getIntent().getExtras();
        if (bundle !=null && bundle.getBoolean(Config.ITEM_FROM_LOCATION_ACTIVITY) ) {
                int itemNumb  = bundle.getInt(Config.ITEM_NUMBER);
                runItemFromDb(itemNumb);

        }else if(DBQueries.getEntityList(this, DBHandle.TABLE_NAME_RECENT).size() != 0){
            runItemFromDb(-1);
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }




        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        Intent intent = new PlaceAutocomplete
                                .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                .build(MainActivity.this);
                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    } catch (GooglePlayServicesRepairableException e) {

                    } catch (GooglePlayServicesNotAvailableException e) {

                    }
                 }
        });


        setupButtonToLocations();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Place place = PlaceAutocomplete.getPlace(this,data);

                presenter.onGetInfo(false,place,"metric", "ru-RUS");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        bundle = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                //gps permission granted
                if (grantResults.length != 0 && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                    //do a gps request
                    //noinspection MissingPermission
                    presenter.onGetInfo(true);
                } else if (DBQueries.getEntityList(getApplicationContext(), DBHandle.TABLE_NAME_RECENT).size() != 0) {
                    {
                            runItemFromDb(-1);
                    }
                }else {
                    Intent intent = new Intent(MainActivity.this, LocationsActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }



    @Override
    public void onInputThisDayInfo(ThisDayResponse response) {
        progressBar.setVisibility(View.INVISIBLE);
        slider.setVisibility(View.VISIBLE);
        cityName.setText(response.getName());
        int tempTmp = (int)Math.round(response.getMain().getTemp());
        String sign = (Math.signum(tempTmp) == 1.0f) ? "+" : "-";
        tempNow.setText(sign+tempTmp + " \u2103");
        weatherNow.setText(response.getWeather().get(0).getDescription());
        windNow.setText("wind speed " + response.getWind().getSpeed());
        setDate(date, System.currentTimeMillis(), "EEE, MMMMMMMM dd, HH:mm");
        setDate(sunRise, response.getSys().getSunrise()*1000, "HH:mm");
        setDate(sunSet, response.getSys().getSunset()*1000, "HH:mm");

        ThemeAdapter themeAdapter = new ThemeAdapter(response.getWeather().get(0).getId(),
                getApplicationContext());

        Picasso.with(getApplicationContext())
                .load(themeAdapter.getBackground())
                .fit()
                .centerCrop()
                .into(mainPic);



        cityName.setTextColor(Color.parseColor(themeAdapter.getTextColor()));
        tempNow.setTextColor(Color.parseColor(themeAdapter.getTextColor()));
        weatherNow.setTextColor(Color.parseColor(themeAdapter.getTextColor()));
        windNow.setTextColor(Color.parseColor(themeAdapter.getTextColor()));
        date.setTextColor(Color.parseColor(themeAdapter.getTextColor()));
        sunRise.setTextColor(Color.parseColor(themeAdapter.getTextColor()));
        sunSet.setTextColor(Color.parseColor(themeAdapter.getTextColor()));


        setupViewPager(response.getCoord().getLat(), response.getCoord().getLon());

    }

    @Override
    public void onOutputError(String e) {

        Toast.makeText(this,"Error, " + e, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context onGetContext() {
        return getApplicationContext();
    }


    private void setUpBroadcastReceiving(){

        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (action.equals(Intent.ACTION_TIME_TICK)
                        || action.equals(Intent.ACTION_TIMEZONE_CHANGED)){

                    setDate(date, System.currentTimeMillis(), "EEE, MMMMMMMM dd, HH:mm");
                }
            }
        };

    }



    private void setDate(TextView v, long time, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        String dateString = sdf.format(time);
        v.setText(dateString);
    }


    private void setupViewPager(double mLat, double mLon){
        InfoPagerAdapter adapter = new InfoPagerAdapter(getSupportFragmentManager(), mLat, mLon);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupButtonToLocations(){



        goToLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(MainActivity.this, LocationsActivity.class);
                        startActivity(intent);
                    }
                }, 600);

                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.back_anim);
                goToLocations.startAnimation(animation);

            }
        });

    }



    private void runItemFromDb(int itemNumb){
        if(presenter == null){
            presenter = new MainActivityPImpl(this);
        }
        List<ItemFromDatabase> list = DBQueries.getEntityList(getApplicationContext(), DBHandle.TABLE_NAME_RECENT);
        int size;
        if(itemNumb < 0) size =list.size() -1;
        else size = itemNumb;
        presenter.onGetInfo(false,
                new Place() {
                    ItemFromDatabase itemFromDatabase = list.get(size);
                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public List<Integer> getPlaceTypes() {
                        return null;
                    }

                    @Override
                    public CharSequence getAddress() {
                        return null;
                    }

                    @Override
                    public Locale getLocale() {
                        return null;
                    }

                    @Override
                    public CharSequence getName() {
                        return itemFromDatabase.getCityName();
                    }

                    @Override
                    public LatLng getLatLng() {
                        return new LatLng(itemFromDatabase.getLat(), itemFromDatabase.getLon());
                    }

                    @Override
                    public LatLngBounds getViewport() {
                        return null;
                    }

                    @Override
                    public Uri getWebsiteUri() {
                        return null;
                    }

                    @Override
                    public CharSequence getPhoneNumber() {
                        return null;
                    }

                    @Override
                    public float getRating() {
                        return 0;
                    }

                    @Override
                    public int getPriceLevel() {
                        return 0;
                    }

                    @Override
                    public CharSequence getAttributions() {
                        return null;
                    }

                    @Override
                    public Place freeze() {
                        return null;
                    }

                    @Override
                    public boolean isDataValid() {
                        return false;
                    }
                },
                "metric", "en");
    }


    


}
