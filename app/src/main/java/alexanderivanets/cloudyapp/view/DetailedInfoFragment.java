package alexanderivanets.cloudyapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alexanderivanets.cloudyapp.adapter.DetailedAdapter;
import alexanderivanets.cloudyapp.R;
import alexanderivanets.cloudyapp.model.ItemDetailedWeather;
import alexanderivanets.cloudyapp.model.fivedayresponse.FiveDayResponse;
import alexanderivanets.cloudyapp.model.fivedayresponse.ListWeather;
import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;
import alexanderivanets.cloudyapp.presenter.DetailedInfoFragmentP;
import alexanderivanets.cloudyapp.presenter.DetailedInfoFragmentPresenter;
import butterknife.BindViews;
import butterknife.ButterKnife;


public class DetailedInfoFragment extends Fragment implements DetailedInfoFragmentV {


    @BindViews({R.id.fragment_date_rv,
            R.id.fragment_weather_rv,
            R.id.fragment_temp_rv,
            R.id.fragment_pressure_rv,
            R.id.fragment_humidity_rv,
            R.id.fragment_wind_speed_rv})
    List<RecyclerView> recyclerViews;

    List<DetailedAdapter> adapters;
    ArrayList<ArrayList<ItemDetailedWeather>> infoList;

    private DetailedInfoFragmentP presenter;
    private double mLat;
    private double mLon;

    private static int CODE_HOURS = 1;
    private static int CODE_DAYS = 2;


    private int FRAGMENT_CODE;

    public DetailedInfoFragment() {
        // Required empty public constructor
    }

    public static DetailedInfoFragment newInstance(int FRAGMENT_CODE, double mLat, double mLon) {
        DetailedInfoFragment fragment = new DetailedInfoFragment();
        Bundle args = new Bundle();
        args.putInt("FRAGMENT_CODE", FRAGMENT_CODE);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if(getArguments() != null){
            FRAGMENT_CODE = getArguments().getInt("FRAGMENT_CODE");
            mLat = getArguments().getDouble("mLat");
            mLon = getArguments().getLong("mLon");

        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_detailed_info, container, false);


        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new DetailedInfoFragmentPresenter(this,mLat,mLon);
        presenter.returnFiveDayInfo(FRAGMENT_CODE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void enterInfoIntoViews(FiveDayResponse response) {

        adapters = new ArrayList<>();
        infoList = new ArrayList<>();

        parseData(infoList, response);

        setCommonLayoutManager(recyclerViews);
        setCommonScrollListener(recyclerViews);
        setAdapters(infoList, recyclerViews);



    }

    @Override
    public void enterInfoIntoViews(ThisDayResponse response) {

    }

    @Override
    public Context getContext(){
        return getView().getContext();
    }

    private void setCommonScrollListener(List<RecyclerView> rvs){

        RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                for (RecyclerView rv:
                        rvs) {
                    if(rv != recyclerView){
                        rv.removeOnScrollListener(this);
                        rv.scrollBy(dx, dy);
                        rv.addOnScrollListener(this);
                    }
                }
            }
        };

        for (RecyclerView rv:
             rvs) {
            rv.addOnScrollListener(listener);
        }


    }

    private void setCommonLayoutManager(List<RecyclerView> rvs){
        for (RecyclerView rv:
             rvs) {
            rv.setLayoutManager(new LinearLayoutManager(
                    getContext(),LinearLayoutManager.HORIZONTAL, false));
        }
    }

    private void parseData(ArrayList<ArrayList<ItemDetailedWeather>> itemsList, FiveDayResponse response){

        ArrayList<ItemDetailedWeather> temp = new ArrayList<>();
        ArrayList<ItemDetailedWeather> date = new ArrayList<>();
        ArrayList<ItemDetailedWeather> weather = new ArrayList<>();
        ArrayList<ItemDetailedWeather> pressure = new ArrayList<>();
        ArrayList<ItemDetailedWeather> humidity = new ArrayList<>();
        ArrayList<ItemDetailedWeather> wind_speed = new ArrayList<>();

        for (ListWeather list:
                response.getList()) {

            temp.add(new ItemDetailedWeather(list.getMain().getTemp().toString() ));
            date.add(new ItemDetailedWeather(formatDate(list.getDt()) ) );
            weather.add(new ItemDetailedWeather(list.getWeather().get(0).getDescription() ,
                    list.getWeather().get(0).getId().toString() ) );
            pressure.add(new ItemDetailedWeather(list.getMain().getPressure().toString()));
            humidity.add(new ItemDetailedWeather(list.getMain().getHumidity().toString()));
            wind_speed.add(new ItemDetailedWeather(list.getWind().getSpeed().toString()));
        }


        itemsList.add(date);
        itemsList.add(weather);
        itemsList.add(temp);
        itemsList.add(pressure);
        itemsList.add(humidity);
        itemsList.add(wind_speed);
    }

    private void setAdapters(ArrayList<ArrayList<ItemDetailedWeather>> itemsList,
                             List<RecyclerView> rvs){
        int index = 0;
        for (RecyclerView rv:
             rvs) {
            DetailedAdapter adapter = new DetailedAdapter(itemsList.get(index));
            rv.setAdapter(adapter);
            index ++;
        }
    }

    private String formatDate(long dt){
        Date date = new Date(dt *1000);
        SimpleDateFormat sdf;
        if(FRAGMENT_CODE == CODE_HOURS){
            sdf = new SimpleDateFormat("HH:mm");
        }else {
            sdf = new SimpleDateFormat("EEE , d MMM HH:mm");
        }
        String formatedData =  sdf.format(date);
        return formatedData;
    }

}
