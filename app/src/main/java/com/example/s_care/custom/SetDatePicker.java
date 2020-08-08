package com.example.s_care.custom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class SetDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText mEditText;
    private Calendar mCalendar;
    private Context mContext;

    {
        mCalendar = Calendar.getInstance();
    }

    public SetDatePicker(EditText ed, Context context){
        mEditText = ed;
        mContext = context;
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);

        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH,month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        mEditText.setText(simpleDateFormat.format(mCalendar.getTime()));


    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(mContext, this,year, month, day);
    }
}
