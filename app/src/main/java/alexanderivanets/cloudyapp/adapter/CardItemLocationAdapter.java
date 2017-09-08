package alexanderivanets.cloudyapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import alexanderivanets.cloudyapp.R;
import alexanderivanets.cloudyapp.model.CardItemFromDatabase;
import alexanderivanets.cloudyapp.utils.CardItemOnClickListener;

/**
 * Created by root on 07.09.17.
 */

public class CardItemLocationAdapter extends RecyclerView.Adapter<CardItemLocationViewHolder> {
    private ArrayList<CardItemFromDatabase> list;

    public CardItemLocationAdapter(ArrayList<CardItemFromDatabase> list){
        this.list = list;
    }


    @Override
    public CardItemLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detailed_weather,parent, false);

        return new CardItemLocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardItemLocationViewHolder holder, int position) {
        holder.cityName.setText(list.get(position).getCityName());
        holder.cityWeather.setText(list.get(position).getWeather());

        if (list.get(position).getFavourite()) {
            //holder.isFavourite.setImageBitmap();
        } else {
            //holder.isFavourite.setImageBitmap();
        }

        holder.isFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(holder.getAdapterPosition()).getFavourite()){
                    //holder.isFavourite.setImageBitmap();
                    list.get(holder.getAdapterPosition()).setFavourite(false);
                }
                else {
                    //holder.isFavourite.setImageBitmap();
                    list.get(holder.getAdapterPosition()).setFavourite(false);
                }
            }
        });


    }




    @Override
    public int getItemCount() {
        return list.size();
    }


}
