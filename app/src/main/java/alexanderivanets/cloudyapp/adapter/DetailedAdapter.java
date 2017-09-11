package alexanderivanets.cloudyapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
            if (items.get(position).getmDescription() != null){
                holder.imageView.setVisibility(View.VISIBLE);
                setWeatherIcon(holder.imageView , Integer.valueOf(items.get(position).getmDescription()) );
            }

    }

    @Override
    public int getItemCount() {
        return items.size();
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
