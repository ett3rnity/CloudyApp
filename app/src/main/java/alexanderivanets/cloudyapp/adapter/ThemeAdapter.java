package alexanderivanets.cloudyapp.adapter;

import android.content.Context;

import alexanderivanets.cloudyapp.R;
import alexanderivanets.cloudyapp.model.Theme;

/**
 * Created by root on 09.08.17.
 */

public class ThemeAdapter {

    private Theme theme;



    public ThemeAdapter(int weatherId, Context context){

        theme = new Theme();

        switch (weatherId){
            case 800:
                theme.setImage(R.drawable.sunny);
                theme.setTextColor("#ffffff");
                break;

            case 801:
                theme.setImage(R.drawable.few_clouds);
                theme.setTextColor("#ffffff");
                break;

            case 803:
                theme.setImage(R.drawable.broken_clouds);
                theme.setTextColor("#ffffff");
                break;

            case 802:
                theme.setImage(R.drawable.skattered_clouds);
                theme.setTextColor("#ffffff");
                break;

            case 701:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
            case 761:
            case 762:
            case 771:
            case 781:
                theme.setImage(R.drawable.mist);
                theme.setTextColor("#ffffff");
                break;

            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 522:
            case 531:
                theme.setImage(R.drawable.rain);
                theme.setTextColor("#000000");
                break;

            case 521:
                theme.setImage(R.drawable.shower_rain);
                theme.setTextColor("#000000");
                break;

            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
                theme.setImage(R.drawable.thunderstorm);
                theme.setTextColor("#ffffff");
                break;

            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                theme.setImage(R.drawable.snow);
                theme.setTextColor("#ffffff");
                break;

            default:
                theme.setImage(R.drawable.sunny);
                theme.setTextColor("#ffffff");
                break;
        }


    }



    public int getBackground(){
        return theme.getImage();
    }

    public String getTextColor(){
        return theme.getTextColor();
    }


}
