package alexanderivanets.cloudyapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import alexanderivanets.cloudyapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 07.09.17.
 */

public class CardItemLocationViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_item_location_weather)
    ImageView weatherIcon;
    @BindView(R.id.tv_item_location_cityName)
    TextView cityName;
    @BindView(R.id.tv_item_location_cityWeather)
    TextView cityWeather;
    @BindView(R.id.iv_item_location_isFavourite)
    ImageView isFavourite;
    @BindView(R.id.rl_item_location)
    RelativeLayout layout;

    public CardItemLocationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }
}
