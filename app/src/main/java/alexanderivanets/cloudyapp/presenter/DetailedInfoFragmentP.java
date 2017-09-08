package alexanderivanets.cloudyapp.presenter;

import alexanderivanets.cloudyapp.model.fivedayresponse.FiveDayResponse;

/**
 * Created by root on 21.08.17.
 */

public interface DetailedInfoFragmentP {
    //infoCode - code of the actual info(hour or daily info)
    void returnFiveDayInfo(int infoCode);
}
