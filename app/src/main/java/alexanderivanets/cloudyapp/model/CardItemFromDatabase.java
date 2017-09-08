package alexanderivanets.cloudyapp.model;

/**
 * Created by root on 07.09.17.
 */

public class CardItemFromDatabase {
    private String cityName;
    private String weather;
    private boolean isFavourite;

    public CardItemFromDatabase(String cityName, String weather, boolean isFavourite){
        this.cityName = cityName;
        this.weather = weather;
        this.isFavourite = isFavourite;
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

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
