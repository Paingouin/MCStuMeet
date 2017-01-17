package com.example.cogo.mcstumeet.fragments;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;

/**
 * Created by Gamze on 16.01.2017.
 */

public class DatePickerFragment extends DialogFragment {

    @TargetApi(25)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceBundle){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)
                        getActivity(), year, month, day);
    }
}
