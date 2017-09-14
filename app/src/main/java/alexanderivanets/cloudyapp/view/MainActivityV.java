package alexanderivanets.cloudyapp.view;

import android.content.Context;

import alexanderivanets.cloudyapp.model.fivedayresponse.FiveDayResponse;
import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;


/**
 * Created by root on 10.07.17.
 */

public interface MainActivityV {

    void onInputThisDayInfo(ThisDayResponse response);

    void onOutputError(String e);

    Context onGetContext();
}
