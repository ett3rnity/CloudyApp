package alexanderivanets.cloudyapp.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 05.09.17.
 */

public class DBHandle extends SQLiteOpenHelper {
    private Context context;

    public static String DB_NAME = "cloudyDB.db";
    public static String TABLE_NAME_RECENT = "locationsList";
    public static String TABLE_NAME_FAVOURITE = "favouriteList";
    private final String createStart = "CREATE TABLE  IF NOT EXISTS ";
    private final String createEnd = "(cityName TEXT, lat REAL, lon REAL);";

    public static final String CITY_NAME_TXT = "cityName";
    public static final String LAT_TXT = "lat";
    public static final String LON_TXT = "lon";

    public DBHandle(Context context, int version) {
        super(context, DB_NAME, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createStart + TABLE_NAME_RECENT + createEnd);
        db.execSQL(createStart + TABLE_NAME_FAVOURITE + createEnd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
