package alexanderivanets.cloudyapp.utils;

import android.view.View;

import alexanderivanets.cloudyapp.model.CardItemFromDatabase;

/**
 * Created by root on 07.09.17.
 */

public interface CardItemOnClickListener{
    void shortClick(int position, CardItemFromDatabase item);
}
