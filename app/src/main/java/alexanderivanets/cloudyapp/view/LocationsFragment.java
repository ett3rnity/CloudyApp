package alexanderivanets.cloudyapp.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import alexanderivanets.cloudyapp.R;
import alexanderivanets.cloudyapp.adapter.CardItemLocationAdapter;
import alexanderivanets.cloudyapp.model.CardItemFromDatabase;
import alexanderivanets.cloudyapp.presenter.LocationsFragmentP;
import alexanderivanets.cloudyapp.presenter.LocationsFragmentPImpl;
import alexanderivanets.cloudyapp.utils.CardItemOnClickListener;
import alexanderivanets.cloudyapp.utils.database.DBHandle;
import alexanderivanets.cloudyapp.utils.database.DBQueries;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsFragment extends Fragment  implements LocationsFragmentV{

    private int fragmentType;
    private CardItemLocationAdapter adapter;
    private LocationsFragmentP presenter;

    @BindView(R.id.rv_locations_fragment)
    RecyclerView recyclerView;

    public LocationsFragment() {

    }

    public static LocationsFragment newInstance(int fragmentType) {
        LocationsFragment fragment = new LocationsFragment();
        Bundle args = new Bundle();
        args.putInt("fragmentType", fragmentType);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentType = getArguments().getInt("fragmentType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_locations, container, false);
        ButterKnife.bind(this,v);

        String tableName;
        if(fragmentType == 1) tableName = DBHandle.TABLE_NAME_RECENT;
        else  tableName = DBHandle.TABLE_NAME_FAVOURITE;

        presenter = new LocationsFragmentPImpl(this, getContext(),tableName);
        presenter.getCardItemList();

        return v;
    }


    @Override
    public void setInfoIntoCards(ArrayList<CardItemFromDatabase> list) {
            adapter = new CardItemLocationAdapter(list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return null;
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                }

                @Override
                public int getItemCount() {
                    return 0;
                }
            });
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).getCityName());
        }
            recyclerView.setAdapter(adapter);
    }
}
