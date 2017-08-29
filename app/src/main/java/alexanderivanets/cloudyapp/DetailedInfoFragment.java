package alexanderivanets.cloudyapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alexanderivanets.cloudyapp.model.ItemDetailedWeather;
import alexanderivanets.cloudyapp.model.fivedayresponse.FiveDayResponse;
import alexanderivanets.cloudyapp.model.fivedayresponse.ListWeather;
import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailedInfoFragment extends Fragment implements DetailedInfoFragmentV {

    @BindView(R.id.fragment_date_rv)
    RecyclerView fragment_date_rv;
    @BindView(R.id.fragment_weather_rv)
    RecyclerView fragment_weather_rv;
    @BindView(R.id.fragment_temp_rv)
    RecyclerView fragment_temp_rv;
    @BindView(R.id.fragment_pressure_rv)
    RecyclerView fragment_pressure_rv;
    @BindView(R.id.fragment_humidity_rv)
    RecyclerView fragment_humidity_rv;
    @BindView(R.id.fragment_wind_descr_rv)
    RecyclerView fragment_wind_descr_rv;
    @BindView(R.id.fragment_wind_speed_rv)
    RecyclerView fragment_wind_speed_rv;



    private DetailedInfoFragmentP presenter;



    public DetailedInfoFragment() {
        // Required empty public constructor
    }

    public static DetailedInfoFragment newInstance() {
        DetailedInfoFragment fragment = new DetailedInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        presenter = new DetailedInfoFragmentPresenter(this);
        presenter.returnFiveDayInfo();
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

        ArrayList<ItemDetailedWeather> temp = new ArrayList<>();
        ArrayList<ItemDetailedWeather> date = new ArrayList<>();
        ArrayList<ItemDetailedWeather> weather = new ArrayList<>();
        ArrayList<ItemDetailedWeather> pressure = new ArrayList<>();
        ArrayList<ItemDetailedWeather> humidity = new ArrayList<>();
        //ArrayList<ItemDetailedWeather> wind_descr = new ArrayList<>();
        ArrayList<ItemDetailedWeather> wind_speed = new ArrayList<>();

        for (ListWeather list:
             response.getList()) {
            temp.add(new ItemDetailedWeather(list.getMain().getTemp().toString()));
            date.add(new ItemDetailedWeather(list.getDtTxt()));
            weather.add(new ItemDetailedWeather(list.getWeather().get(0).getDescription()));
            pressure.add(new ItemDetailedWeather(list.getMain().getPressure().toString()));
            humidity.add(new ItemDetailedWeather(list.getMain().getHumidity().toString()));
            wind_speed.add(new ItemDetailedWeather(list.getWind().getSpeed().toString()));
        }


        DetailedAdapter dateAdapter = new DetailedAdapter(date);
        DetailedAdapter tempAdapter = new DetailedAdapter(temp);
        DetailedAdapter weatherAdapter = new DetailedAdapter(weather);
        DetailedAdapter windSpeedAdapter = new DetailedAdapter(wind_speed);
        DetailedAdapter pressureAdapter = new DetailedAdapter(pressure);
        DetailedAdapter humidityAdapter = new DetailedAdapter(humidity);

        fragment_humidity_rv.setLayoutManager(new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL, false ));
        fragment_pressure_rv.setLayoutManager(new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL, false));
        fragment_wind_speed_rv.setLayoutManager(new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL, false));
        fragment_temp_rv.setLayoutManager(new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL, false));
        fragment_weather_rv.setLayoutManager(new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL, false));
        fragment_date_rv.setLayoutManager(new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL, false));


        fragment_date_rv.setAdapter(dateAdapter);
        fragment_temp_rv.setAdapter(tempAdapter);
        fragment_weather_rv.setAdapter(weatherAdapter);
        fragment_wind_speed_rv.setAdapter(windSpeedAdapter);
        fragment_pressure_rv.setAdapter(pressureAdapter);
        fragment_humidity_rv.setAdapter(humidityAdapter);

        dateAdapter.notifyDataSetChanged();
        tempAdapter.notifyDataSetChanged();
        weatherAdapter.notifyDataSetChanged();
        windSpeedAdapter.notifyDataSetChanged();
        pressureAdapter.notifyDataSetChanged();
        humidityAdapter.notifyDataSetChanged();



    }

    @Override
    public void enterInfoIntoViews(ThisDayResponse response) {

    }

    @Override
    public Context getContext(){
        return getView().getContext();
    }


}
