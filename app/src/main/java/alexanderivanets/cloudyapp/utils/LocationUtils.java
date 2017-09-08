package alexanderivanets.cloudyapp.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by root on 11.08.17.
 */

public class LocationUtils {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Context context;


    public LocationUtils(Context context) {
        this.context = context;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public FusedLocationProviderClient returnRegisteredProvider() {
        return mFusedLocationProviderClient;

    }
}
