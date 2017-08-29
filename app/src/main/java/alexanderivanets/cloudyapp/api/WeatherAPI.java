package alexanderivanets.cloudyapp.api;

import alexanderivanets.cloudyapp.model.fivedayresponse.FiveDayResponse;
import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by root on 09.07.17.
 */

public interface WeatherAPI {
    @GET("/data/2.5/forecast")
    Observable<FiveDayResponse> getFiveDayResponse(@Query("q") String mCityName,
                                            @Query("units")String mUnits,
                                            @Query("lang") String mLang,
                                            @Query("appid") String mApiKey);


    @GET("/data/2.5/weather")
    Observable<ThisDayResponse> getThisDayResponse(@Query("q") String mCityName,
                                                   @Query("units")String mUnits,
                                                   @Query("lang") String mLang,
                                                   @Query("appid") String mApiKey);

    @GET("/data/2.5/weather")
    Observable<ThisDayResponse> getThisDayResponseCoord(@Query("lat") String mLat,
                                                        @Query("lon") String mLon,
                                                        @Query("units")String mUnits,
                                                        @Query("lang") String mLang,
                                                        @Query("appid") String mApiKey);


}
