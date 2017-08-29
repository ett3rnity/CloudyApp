package alexanderivanets.cloudyapp;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import alexanderivanets.cloudyapp.api.RetrofitSingleton;
import alexanderivanets.cloudyapp.api.WeatherAPI;


import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 10.07.17.
 */


public class MainActivityPImpl implements MainActivityP {

    private Place place;
    private String units;
    private String lang;
    private MainActivityV view;
    private Observable<ThisDayResponse> observable;
    private DisposableObserver<ThisDayResponse> observer;
    private WeatherAPI api;

    private LocationUtils locationUtils;


    public MainActivityPImpl(MainActivityV view) {
        this.view = view;
        api = RetrofitSingleton.buildApi();
    }

    @Override
    public void onGetInfo(boolean searchByGps) {
        mainInfo(searchByGps);

    }

    @Override
    public void onGetInfo(boolean searchByGps, Place place, String units, String lang) {
        this.place = place;
        this.units = units;
        this.lang = lang;

        mainInfo(searchByGps);
    }




    private void mainInfo(boolean gpsEnabled){
        observer = new DisposableObserver<ThisDayResponse>() {


            @Override
            public void onNext(@NonNull ThisDayResponse thisDayResponse) {
                view.onInputThisDayInfo(thisDayResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {

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
                                    String.valueOf(location.getLatitude()),
                                    String.valueOf(location.getLongitude()),
                                    units,
                                    lang,
                                    Config.WEATHER_API_KEY
                            );

                            subscribeObservable(observable,observer);
                        }
                    });

    }

    private void runObservable(Place place){
        String mLat = String.valueOf(place.getLatLng().latitude);
        String mLon = String.valueOf(place.getLatLng().longitude);
        observable = api.getThisDayResponseCoord(mLat, mLon, units, lang, Config.WEATHER_API_KEY);

        subscribeObservable(observable,observer);
    }


    private void subscribeObservable(Observable<ThisDayResponse> observable, Observer<ThisDayResponse> observer){
        observable
                .retryWhen(throwableObservable -> throwableObservable.delay(5,TimeUnit.SECONDS))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }





}


