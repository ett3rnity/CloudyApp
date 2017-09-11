package alexanderivanets.cloudyapp.presenter;

import android.util.TimeUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;

import alexanderivanets.cloudyapp.model.Config;
import alexanderivanets.cloudyapp.view.DetailedInfoFragment;
import alexanderivanets.cloudyapp.view.DetailedInfoFragmentV;
import alexanderivanets.cloudyapp.api.RetrofitSingleton;
import alexanderivanets.cloudyapp.api.WeatherAPI;
import alexanderivanets.cloudyapp.model.fivedayresponse.FiveDayResponse;
import alexanderivanets.cloudyapp.model.fivedayresponse.ListWeather;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 21.08.17.
 */

public class DetailedInfoFragmentPresenter implements DetailedInfoFragmentP{

    private DetailedInfoFragmentV view;
    private Observable<FiveDayResponse> observable;
    private Observer<FiveDayResponse> observer;
    private WeatherAPI api;


    private double mLat;
    private double mLon;
    private static int CODE_HOURS = 1;
    private static int CODE_DAYS = 2;


    public DetailedInfoFragmentPresenter(DetailedInfoFragmentV view, double mLat, double mLon){
        this.view = view;
        api = RetrofitSingleton.buildApi();
        this.mLat = mLat;
        this.mLon = mLon;
    }


    @Override
    public void returnFiveDayInfo(int code) {

        // FIXME: 21.08.17
        String mUnits = "metric";
        String mLang = "en";
        observable = api.getFiveDayResponse(mLat, mLon, mUnits, mLang, Config.WEATHER_API_KEY);

        observer = new Observer<FiveDayResponse>() {
            @Override
            public void onSubscribe( Disposable d) {

            }

            @Override
            public void onNext( FiveDayResponse response) {

                long endOfDay = getEndOfDay(new Date(System.currentTimeMillis())).getTime()/1000;

                List<ListWeather> newList = new ArrayList<>();

                for (int i=0; i<response.getList().size(); i++) {

                    ListWeather listWeather = response.getList().get(i);

                    if(code == CODE_HOURS) {
                        if (listWeather.getDt() < endOfDay) {
                            newList.add(listWeather);
                        }
                    }

                    else if (code == CODE_DAYS){
                        if (listWeather.getDt() > endOfDay) {
                        newList.add(listWeather);
                        }
                    }

                }

                response.setList(newList);

                view.enterInfoIntoViews(response);

            }

            @Override
            public void onError( Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        subscribeObservable(observable, observer);

    }


    private void subscribeObservable(Observable<FiveDayResponse> observable,
                                     Observer<FiveDayResponse> observer){


        observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


}
