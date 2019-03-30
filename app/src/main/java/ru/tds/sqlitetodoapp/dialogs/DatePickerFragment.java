package ru.tds.sqlitetodoapp.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * DataPicker Dialog created by @trushenkoff.d [insta]
 *
 * @see <a href="http://instagram.com/trushenkoff.d">Instagram</a>
 * <p>
 * Created by Trushenkov Dmitry on 28.03.2019
 */
public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // get current date from phone
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        // open Date Picker Dialog
        return new DatePickerDialog(getActivity(),
                (DatePickerDialog.OnDateSetListener) getActivity(),
                year,
                month,
                dayOfMonth);
    }
}
