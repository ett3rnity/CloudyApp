package alexanderivanets.cloudyapp;

import android.support.annotation.MainThread;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import alexanderivanets.cloudyapp.api.RetrofitSingleton;
import alexanderivanets.cloudyapp.api.WeatherAPI;
import alexanderivanets.cloudyapp.model.fivedayresponse.FiveDayResponse;
import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 21.08.17.
 */

public class DetailedInfoFragmentPresenter implements DetailedInfoFragmentP{


    // FIXME: 21.08.17 TEST VALUES

    private DetailedInfoFragmentV view;
    private Observable<FiveDayResponse> observable;
    private Observer<FiveDayResponse> observer;
    private WeatherAPI api;


    public DetailedInfoFragmentPresenter(DetailedInfoFragmentV view){
        this.view = view;
        api = RetrofitSingleton.buildApi();

    }


    @Override
    public void returnFiveDayInfo() {

        // FIXME: 21.08.17
        String mCity = "Kiyev";
        String mUnits = "metric";
        String mLang = "en";
        observable = api.getFiveDayResponse(mCity, mUnits, mLang, Config.WEATHER_API_KEY);

        observer = new Observer<FiveDayResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Toast.makeText(view.getContext(), "SUB", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(@NonNull FiveDayResponse response) {
                view.enterInfoIntoViews(response);
                Toast.makeText(view.getContext(), "NEXT", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(view.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(view.getContext(), "COMPLETE", Toast.LENGTH_SHORT).show();
            }
        };

        subscribeObservable(observable, observer);

    }


    private void subscribeObservable(Observable<FiveDayResponse> observable, Observer<FiveDayResponse> observer){
        observable
                //.retryWhen(throwableObservable -> throwableObservable.delay(5,TimeUnit.SECONDS))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
