package alexanderivanets.cloudyapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import alexanderivanets.cloudyapp.R;
import alexanderivanets.cloudyapp.model.CardItemFromDatabase;
import alexanderivanets.cloudyapp.model.Config;
import alexanderivanets.cloudyapp.model.PlaceBuilder;
import alexanderivanets.cloudyapp.utils.database.DBHandle;
import alexanderivanets.cloudyapp.utils.database.DBQueries;
import alexanderivanets.cloudyapp.view.MainActivity;

/**
 * Created by root on 07.09.17.
 */

public class CardItemLocationAdapter extends RecyclerView.Adapter<CardItemLocationViewHolder>{
    private ArrayList<CardItemFromDatabase> list;
    private Context context;


    public CardItemLocationAdapter(ArrayList<CardItemFromDatabase> list, Context context){
        this.list = list;
        this.context = context;
    }


    @Override
    public CardItemLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location,parent, false);

        return new CardItemLocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardItemLocationViewHolder holder, int position) {
        holder.cityName.setText(list.get(position).getCityName());
        holder.cityWeather.setText(list.get(position).getWeather());

        if (list.get(position).getFavourite()) {
            holder.isFavourite.setImageResource(R.drawable.ic_heart_checked);
        } else {
            holder.isFavourite.setImageResource(R.drawable.ic_heart_unchecked);
        }


        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Config.ITEM_FROM_LOCATION_ACTIVITY,true);
            intent.putExtra(Config.ITEM_NUMBER, list.get(holder.getAdapterPosition()).getNumb() );
            context.startActivity(intent);

        });

        holder.isFavourite.setOnClickListener(v -> {
            if(list.get(holder.getAdapterPosition()).getFavourite()){
                DBQueries.deleteFromDatabase(context, DBHandle.TABLE_NAME_FAVOURITE,
                        list.get(holder.getAdapterPosition() ).getCityName());
                holder.isFavourite.setImageResource(R.drawable.ic_heart_unchecked);
                list.get(holder.getAdapterPosition()).setFavourite(false);
            }
            else {
                CardItemFromDatabase bufItem = list.get(holder.getAdapterPosition());
                DBQueries.addToDatabase(PlaceBuilder.newBuilder().setName(bufItem.getCityName())
                            .setLatLng( new LatLng(bufItem.getLat(),bufItem.getLon()) )
                            .build(),
                            context,
                            DBHandle.TABLE_NAME_FAVOURITE);
                holder.isFavourite.setImageResource(R.drawable.ic_heart_checked);
                list.get(holder.getAdapterPosition()).setFavourite(true);
            }
        });

        setWeatherIcon(holder.weatherIcon, list.get(holder.getAdapterPosition()).getWeatherCode());

    }




    @Override
    public int getItemCount() {
        return list.size();
    }


    private  void setWeatherIcon(ImageView imageView, int weather){
        switch (weather){
            case 800:
                imageView.setImageResource(R.drawable.ic_sunny);
                break;

            case 801:
            case 802:
            case 803:
                imageView.setImageResource(R.drawable.ic_clouds);
                break;

            case 701:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
            case 761:
            case 762:
            case 771:
            case 781:
                imageView.setImageResource(R.drawable.ic_mist);
                break;

            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 522:
            case 531:
            case 521:
                imageView.setImageResource(R.drawable.ic_rain);
                break;

            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
                imageView.setImageResource(R.drawable.ic_thunderstorm);
                break;

            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                imageView.setImageResource(R.drawable.ic_snow);
                break;

            default:
                imageView.setImageResource(R.drawable.ic_sunny);
                break;
        }
    }


}
