package alexanderivanets.cloudyapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 20.08.17.
 */

public class DetailedViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_detailed_name)
    TextView item;
    @BindView(R.id.item_detailed_descr)
    TextView description;

    public DetailedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
