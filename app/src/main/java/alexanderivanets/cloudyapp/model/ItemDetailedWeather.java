package alexanderivanets.cloudyapp.model;

/**
 * Created by root on 20.08.17.
 */

public class ItemDetailedWeather {

    private String mInfo;
    private String mDescription;

    public ItemDetailedWeather(String mInfo){
        this.mInfo = mInfo;
    }

    public ItemDetailedWeather(String mInfo, String mDescription){
        this.mInfo = mInfo;
        this.mDescription = mDescription;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmInfo() {
        return mInfo;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmInfo(String mInfo) {
        this.mInfo = mInfo;
    }
}
