package alexanderivanets.cloudyapp.api;

import alexanderivanets.cloudyapp.Config;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 21.08.17.
 */

public class RetrofitSingleton {
    private static Retrofit retrofit;
    private static WeatherAPI api;


    public static WeatherAPI buildApi(){
        if(api == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            api = retrofit.create(WeatherAPI.class);
            return api;
        }
        else return api;
    }

}
