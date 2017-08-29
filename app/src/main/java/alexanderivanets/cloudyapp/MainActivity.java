package alexanderivanets.cloudyapp;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mancj.slideup.SlideUp;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.security.PermissionCollection;
import java.security.Permissions;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import alexanderivanets.cloudyapp.model.fivedayresponse.FiveDayResponse;
import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityV {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;

    private MainActivityP presenter;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;



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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpBroadcastReceiving();
        this.registerReceiver(broadcastReceiver, intentFilter);

        presenter = new MainActivityPImpl(this);



        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);


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

        setupViewPager();



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
                }
                else{
                        //do a city name request
                        presenter.onGetInfo(false);
                    }

                    break;

                }
        }


    @Override
    public void onInputFiveDayInfo(FiveDayResponse response) {

    }

    @Override
    public void onInputThisDayInfo(ThisDayResponse response) {
        cityName.setText(response.getName());
        int tempTmp = (int)Math.round(response.getMain().getTemp());
        String sign = (Math.signum(tempTmp) == 1.0f) ? "+" : "-";
        tempNow.setText(sign+tempTmp);
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


    private void setupViewPager(){
        InfoPagerAdapter adapter = new InfoPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
