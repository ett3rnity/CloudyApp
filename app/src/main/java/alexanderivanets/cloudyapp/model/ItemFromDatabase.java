package alexanderivanets.cloudyapp.model;

/**
 * Created by root on 06.09.17.
 */

public class ItemFromDatabase {
    private String cityName;
    private double lat;
    private double lon;


    public ItemFromDatabase(String cityName, double lat, double lon){
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
    }


    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }



}
