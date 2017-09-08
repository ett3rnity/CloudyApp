package alexanderivanets.cloudyapp.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.location.places.Place;

import alexanderivanets.cloudyapp.utils.database.DBHandle;
import alexanderivanets.cloudyapp.view.LocationsActivityV;

/**
 * Created by root on 06.09.17.
 */

public class LocationsActivityPImpl implements LocationsActivityP {

    private LocationsActivityV view;
    private Context context;


    public LocationsActivityPImpl(LocationsActivityV view, Context context){
        this.view = view;
        this.context = context;
    }

}
