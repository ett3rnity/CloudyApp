package alexanderivanets.cloudyapp.presenter;


import android.location.Location;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.tasks.OnSuccessListener;

import alexanderivanets.cloudyapp.model.Config;
import alexanderivanets.cloudyapp.utils.LocationUtils;
import alexanderivanets.cloudyapp.view.MainActivityV;
import alexanderivanets.cloudyapp.api.RetrofitSingleton;
import alexanderivanets.cloudyapp.api.WeatherAPI;


import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 10.07.17.
 */


public class MainActivityPImpl implements MainActivityP {

    private Place place;
    private String units = "metric";
    private String lang = "ru";
    private MainActivityV view;
    private Observable<ThisDayResponse> observable;
    private DisposableObserver<ThisDayResponse> observer;
    private WeatherAPI api;

    private boolean searchByGps;
    private LocationUtils locationUtils;




    public MainActivityPImpl(MainActivityV view) {
        this.view = view;
        api = RetrofitSingleton.buildApi();
    }

    @Override
    public void onGetInfo(boolean searchByGps) {
        this.searchByGps = searchByGps;
        mainInfo(searchByGps);

    }

    @Override
    public void onGetInfo(boolean searchByGps, Place place, String units, String lang) {
        this.searchByGps =searchByGps;
        this.place = place;
        this.units = units;
        this.lang = lang;

        mainInfo(searchByGps);
    }




    private void mainInfo(boolean gpsEnabled){
        observer = new DisposableObserver<ThisDayResponse>() {


            @Override
            public void onNext(ThisDayResponse thisDayResponse) {
                if (!searchByGps){
                    if (place != null){
                        thisDayResponse.setName(place.getName().toString());
                    }
                }
                view.onInputThisDayInfo(thisDayResponse);
            }

            @Override
            public void onError( Throwable e) {
                view.onOutputError(e.getLocalizedMessage());
            }


            @Override
            public void onComplete() {
                if(!isDisposed()) dispose();
            }
        };

        if(gpsEnabled) {
            runObservable();
        }

        else{
            if(place != null){
                runObservable(place);
            }
        }
    }


    private void runObservable(){

            locationUtils = new LocationUtils(view.onGetContext());
            //noinspection MissingPermission
            locationUtils.returnRegisteredProvider().getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            observable = api.getThisDayResponseCoord(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    units,
                                    lang,
                                    Config.WEATHER_API_KEY
                            );

                            subscribeObservable(observable,observer);
                        }
                    });

    }

    private void runObservable(Place place){
        observable = api.getThisDayResponseCoord(place.getLatLng().latitude
                , place.getLatLng().longitude, units, lang, Config.WEATHER_API_KEY);

        subscribeObservable(observable,observer);
    }


    private void subscribeObservable(Observable<ThisDayResponse> observable, Observer<ThisDayResponse> observer){
        observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }





}


