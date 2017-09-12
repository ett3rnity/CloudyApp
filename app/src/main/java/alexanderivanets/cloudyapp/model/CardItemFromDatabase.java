package alexanderivanets.cloudyapp.model;

/**
 * Created by root on 07.09.17.
 */

public class CardItemFromDatabase {
    private String cityName;
    private String weather;
    private boolean isFavourite;
    private int weatherCode;
    private double lat;
    private double lon;
    private int numb;

    public CardItemFromDatabase(String cityName, String weather, boolean isFavourite, int weatherCode,
                                double lat, double lon, int numb){
        this.cityName = cityName;
        this.weather = weather;
        this.isFavourite = isFavourite;
        this.weatherCode = weatherCode;
        this.lat = lat;
        this.lon = lon;
        this.numb = numb;
    }

    public String getCityName() {
        return cityName;
    }

    public String getWeather() {
        return weather;
    }

    public boolean getFavourite(){
        return  isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getWeatherCode() {
        return weatherCode;
    }


    public double getLat(){
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getNumb() {
        return numb;
    }

    public void setNumb(int numb) {
        this.numb = numb;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
