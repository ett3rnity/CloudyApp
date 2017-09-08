package alexanderivanets.cloudyapp.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;
import java.util.List;

import alexanderivanets.cloudyapp.model.ItemFromDatabase;

/**
 * Created by root on 06.09.17.
 */

public class DBQueries {
    private static DBHandle dbHandle;
    private static SQLiteDatabase database;

    private static final String SELECT_ALL = "SELECT * FROM ";
    private static final String DELETE_FROM = "DELETE FROM ";
    private static final String WHERE = "WHERE";
    private static final String EQUALS = "EQUALS";


    public static ArrayList<ItemFromDatabase> getEntityList(Context context, String tableName){

        //listName- recent or favourite

        ArrayList<ItemFromDatabase> list = new ArrayList<>();

        if(dbHandle == null){
            dbHandle = new DBHandle(context, 1);
        }

        database = dbHandle.getReadableDatabase();

        Cursor cursor = database.rawQuery(SELECT_ALL + tableName, null);

        /*if(!cursor.moveToFirst()){
            return list;
        } else {
            list.add(fillItem(cursor));
        }

        if(!cursor.moveToNext()){
            return list;
        }else {
            do{
                list.add(fillItem(cursor));
            }while (cursor.moveToNext());
        }
        */


        cursor.close();

        return list;
    }


    public static void addToDatabase(Place place, Context context, String tableName){
        if(dbHandle == null){
            dbHandle = new DBHandle(context, 1);
        }

        database = dbHandle.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHandle.LAT_TXT, place.getLatLng().latitude);
        cv.put(DBHandle.LON_TXT, place.getLatLng().longitude);
        cv.put(DBHandle.LON_TXT, place.getName().toString());
        database.insert(tableName, null, cv);

        database.close();
    }



    public static void deleteFromDatabase(Context context, String tableName, String cityName){
        if(dbHandle == null){
            dbHandle = new DBHandle(context, 1);
        }

        database = dbHandle.getWritableDatabase();
        database.execSQL(DELETE_FROM + tableName + WHERE + DBHandle.CITY_NAME_TXT + EQUALS + cityName);
        database.close();
    }


    private static ItemFromDatabase fillItem(Cursor cursor){
        String name = cursor.getString(cursor.getColumnIndex(DBHandle.DB_NAME));
        double lat = cursor.getDouble(cursor.getColumnIndex(DBHandle.LAT_TXT));
        double lon = cursor.getDouble(cursor.getColumnIndex(DBHandle.LON_TXT));

        return new ItemFromDatabase(name, lat, lon);
    }


}
