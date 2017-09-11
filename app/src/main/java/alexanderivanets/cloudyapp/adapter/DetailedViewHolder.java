package alexanderivanets.cloudyapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import alexanderivanets.cloudyapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 20.08.17.
 */

public class DetailedViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_detailed_name)
    TextView item;
    @BindView(R.id.iv_detailed)
    ImageView imageView;

    public DetailedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
