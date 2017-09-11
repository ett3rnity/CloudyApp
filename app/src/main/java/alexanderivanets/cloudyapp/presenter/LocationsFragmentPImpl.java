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

        List<ItemFromDatabase> dbItems = DBQueries.getEntityList(context, tableName);

        for (ItemFromDatabase item:
             dbItems) {
            setObservable(api,item);
        }

    }

    private void setObservable(WeatherAPI api, ItemFromDatabase itemFromDatabase){
        Observable<ThisDayResponse> observable = api.getThisDayResponseCoord(itemFromDatabase.getLat(),
                itemFromDatabase.getLon(), "metric", "en", Config.WEATHER_API_KEY);

        Observer<ThisDayResponse> observer = new Observer<ThisDayResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ThisDayResponse value) {
                boolean isInFavouriteDb = DBQueries.isItemFromDatabase(context,
                        DBHandle.TABLE_NAME_FAVOURITE, value.getName());


                cardList.add(new CardItemFromDatabase(itemFromDatabase.getCityName(),
                        value.getMain().getTemp() + " \u2103",
                        isInFavouriteDb, value.getWeather().get(0).getId(), value.getCoord().getLat(), value.getCoord().getLon() ) );
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
