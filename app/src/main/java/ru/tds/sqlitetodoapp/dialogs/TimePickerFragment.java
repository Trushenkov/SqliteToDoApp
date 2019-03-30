package ru.tds.sqlitetodoapp.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * TimePicker Dialog created by @trushenkoff.d [insta]
 * <p>
 * Created by Trushenkov Dmitry on 28.03.2019
 */
public class TimePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // get current time from phone
        Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // open Time Picker Dialog
        return new TimePickerDialog(getActivity(),
                (TimePickerDialog.OnTimeSetListener) getActivity(),
                hourOfDay,
                minute,
                DateFormat.is24HourFormat(getActivity()));
    }
}
