package alexanderivanets.cloudyapp.view;

import android.content.Context;

import alexanderivanets.cloudyapp.model.fivedayresponse.FiveDayResponse;
import alexanderivanets.cloudyapp.model.thisdayresponse.ThisDayResponse;

/**
 * Created by root on 21.08.17.
 */

public interface DetailedInfoFragmentV {

    void enterInfoIntoViews(FiveDayResponse response);
    void enterInfoIntoViews(ThisDayResponse response);

    Context getContext();
}
