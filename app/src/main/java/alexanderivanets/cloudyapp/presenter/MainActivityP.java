package alexanderivanets.cloudyapp.presenter;

import com.google.android.gms.location.places.Place;

/**
 * Created by root on 10.07.17.
 */

public interface MainActivityP {
    void onGetInfo(boolean searchByGps);
    void onGetInfo(boolean searchByGps, Place place, String units, String lang);
}
