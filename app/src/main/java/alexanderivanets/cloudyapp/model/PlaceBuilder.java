package alexanderivanets.cloudyapp.model;

import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;

/**
 * Created by root on 18.09.17.
 */

public class PlaceBuilder implements Place {
    private String id;
    private List<Integer> placeTypes;
    private CharSequence address;
    private Locale locale;
    private CharSequence name;
    private LatLng latLng;
    private LatLngBounds viewport;
    private Uri websiteUri;
    private CharSequence phoneNumber;
    private float rating;
    private int priceLevel;
    private CharSequence attributions;
    private boolean dataValid;





    public PlaceBuilder(String id,
                        List<Integer> placeTypes,
                        CharSequence address,
                        Locale locale,
                        CharSequence name,
                        LatLng latLng,
                        LatLngBounds viewport,
                        Uri websiteUri,
                        CharSequence phoneNumber,
                        float rating,
                        int priceLevel,
                        CharSequence attributions,
                        boolean dataValid){
        this.address = address;
        this.attributions = attributions;
        this.dataValid = dataValid;
        this.rating = rating;
        this.priceLevel = priceLevel;
        this.placeTypes = placeTypes;
        this.locale = locale;
        this.name = name;
        this.latLng = latLng;
        this.viewport = viewport;
        this.websiteUri = websiteUri;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public PlaceBuilder(){

    }



    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public List<Integer> getPlaceTypes() {
        return this.placeTypes;
    }

    @Override
    public CharSequence getAddress() {
        return this.address;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public CharSequence getName() {
        return this.name;
    }

    @Override
    public LatLng getLatLng() {
        return this.latLng;
    }

    @Override
    public LatLngBounds getViewport() {
        return this.viewport;
    }

    @Override
    public Uri getWebsiteUri() {
        return this.websiteUri;
    }

    @Override
    public CharSequence getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public float getRating() {
        return this.rating;
    }

    @Override
    public int getPriceLevel() {
        return this.priceLevel;
    }

    @Override
    public CharSequence getAttributions() {
        return this.attributions;
    }

    @Override
    public Place freeze() {
        return null;
    }

    @Override
    public boolean isDataValid() {
        return this.dataValid;
    }

    public static Builder newBuilder(){
        return new PlaceBuilder().new Builder();
    }


    public  class Builder{

        private Builder(){
            //private constructor
        }

        public Builder setId(String id){
            PlaceBuilder.this.id = id;

            return this;
        }

        public Builder setDataValid(boolean dataValid){
            PlaceBuilder.this.dataValid = dataValid;

            return this;
        }

        public Builder setPlaceTypes(List<Integer> placeTypes){
            PlaceBuilder.this.placeTypes = placeTypes;

            return this;
        }

        public Builder setAddress(CharSequence address){
            PlaceBuilder.this.address = address;

            return this;
        }

        public Builder setAttributions(CharSequence attributions){
            PlaceBuilder.this.attributions = attributions;
            return this;
        }

        public Builder setRating(float rating){
            PlaceBuilder.this.rating = rating;

            return this;
        }

        public Builder setPriceLevel(int priceLevel){
            PlaceBuilder.this.priceLevel = priceLevel;

            return this;
        }

        public Builder setLocale(Locale locale){
            PlaceBuilder.this.locale = locale;

            return this;
        }
        public Builder setName(CharSequence name){
            PlaceBuilder.this.name = name;

            return this;
        }

        public Builder setLatLng(LatLng latLng){
            PlaceBuilder.this.latLng = latLng;

            return this;
        }

        public Builder setViewPort(LatLngBounds viewPort){
            PlaceBuilder.this.viewport = viewPort;

            return this;
        }

        public Builder setWebsiteUri(Uri websiteUri){
            PlaceBuilder.this.websiteUri = websiteUri;

            return this;
        }

        public Builder setPhoneNumber(CharSequence phoneNumber){
            PlaceBuilder.this.phoneNumber = phoneNumber;

            return this;
        }

        public PlaceBuilder build(){
            return PlaceBuilder.this;
        }

    }



}
