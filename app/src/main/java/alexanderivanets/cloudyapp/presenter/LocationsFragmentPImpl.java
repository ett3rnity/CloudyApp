package alexanderivanets.cloudyapp.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alexanderivanets.cloudyapp.api.RetrofitSingleton;
import alexanderivanets.cloudyapp.api.WeatherAPI;
import alexanderivanets.cloudyapp.model.CardItemFromDatabase;
import alexanderivanets.cloudyapp.model.Config;
import alexanderivanets.cloudyapp.model.ItemFromDatabase;
import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;
import alexanderivanets.cloudyapp.utils.database.DBHandle;
import alexanderivanets.cloudyapp.utils.database.DBQueries;
import alexanderivanets.cloudyapp.view.LocationsFragmentV;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 06.09.17.
 */

public class LocationsFragmentPImpl implements LocationsFragmentP {

    private LocationsFragmentV view;
    private Context context;
    private String tableName;


    private ArrayList<CardItemFromDatabase> cardList;


    public LocationsFragmentPImpl(LocationsFragmentV view, Context context, String tableName){
        this.view = view;
        this.context = context;
        this.tableName = tableName;
        cardList = new ArrayList<>();
    }


    @Override
    public void getCardItemList() {
        WeatherAPI api = RetrofitSingleton.buildApi();

        ArrayList<ItemFromDatabase> dbItems = DBQueries.getEntityList(context, tableName);

        for (ItemFromDatabase item:
             dbItems) {
            setObservable(api,item);
        }

    }

    private void setObservable(WeatherAPI api, ItemFromDatabase itemFromDatabase){
        Observable<ThisDayResponse> observable = api.getThisDayResponse(itemFromDatabase.getCityName(), "metric", "ru-RUS", Config.WEATHER_API_KEY);

        Observer<ThisDayResponse> observer = new Observer<ThisDayResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                Toast.makeText(context, "SUB", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ThisDayResponse value) {

                Toast.makeText(context, "NEXT", Toast.LENGTH_SHORT).show();
                //// FIXME: 07.09.17 check fav db for 3rd parameter
                cardList.add(new CardItemFromDatabase(value.getName(),
                        value.getWeather().get(0).getDescription(),
                        false));
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "LOC FRAGMENT ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                view.setInfoIntoCards(cardList);
            }
        };

        observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
