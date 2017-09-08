package alexanderivanets.cloudyapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import alexanderivanets.cloudyapp.R;
import alexanderivanets.cloudyapp.model.ItemDetailedWeather;



public class DetailedAdapter extends RecyclerView.Adapter<DetailedViewHolder> {
    private ArrayList<ItemDetailedWeather> items;


    public DetailedAdapter(ArrayList<ItemDetailedWeather> items){
        this.items = items;
    }

    @Override
    public DetailedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detailed_weather,parent,false);

        return new DetailedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DetailedViewHolder holder, int position) {
            if(items.get(position).getmInfo() != null)
            { holder.item.setText(items.get(position).getmInfo()); }
            if(items.get(position).getmDescription() != null)
            {    holder.description.setText(items.get(position).getmDescription()); }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
